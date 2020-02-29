package com.thul.filmee.datasource
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.an.github.rest.RestApi
import com.thul.filmee.AppConstants.Companion.TMDB_KEY
import com.thul.filmee.response.CreditResponse
import com.thul.filmee.utils.parseCastCrewData
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import java.util.concurrent.Executor


class PageKeyedCrewDataSource (
                                 val id:Int,
                                 retryExecutor: Executor
) : PageKeyedDataSource<Int, CreditResponse.Crew>()

{
    private val tmdbService:RestApi by lazy {
    RestApi.create()
    }
    var initialParams: LoadInitialParams<Int>? = null
    var afterParams: LoadParams<Int>? = null
    var retry: (() -> Any)? = null
    val networkState = MutableLiveData<NetworkState>()
    val initial = MutableLiveData<NetworkState>()


    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, CreditResponse.Crew>) {
        networkState.postValue(NetworkState.LOADING)
        initial.postValue(NetworkState.LOADING)
        tmdbService.getCastCrew(id, TMDB_KEY)
            .enqueue(object : retrofit2.Callback<ResponseBody> {



                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if (response.isSuccessful && response.code() == 200) {

                        val reviewsResponseString = response.body()?.string()
                        Log.d("RESPONSE-",response.body().toString())
                        val reviewsListing = parseCastCrewData(reviewsResponseString!!)


                        Log.d("MOVIZ-A",response.body().toString())
                        callback.onResult(reviewsListing.crew,null,2)
                        networkState.postValue(NetworkState.LOADED)
                        initial.postValue(NetworkState.LOADED)
                        initialParams = null
                    }

                    else {
                        Log.d("MOVIZ-A","Error occurred:Code ${response.code()}")
                    }


                }
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

                    networkState.postValue(NetworkState.FAILED)
                    initial.postValue(NetworkState.FAILED)
                    Log.d("MOVIZ-A","Error:${t.message}")
                }

            })


    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, CreditResponse.Crew>) {
        afterParams = params
        networkState.postValue(NetworkState.LOADING)
        tmdbService.getCastCrew(id, TMDB_KEY)
            .enqueue(object : retrofit2.Callback<ResponseBody> {

                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

                    if (response.isSuccessful && response.code() == 200) {

                        val reviewsResponseString = response.body()?.string()
                        Log.d("RESPONSE-",response.body().toString())
                        val reviewsListing = parseCastCrewData(reviewsResponseString!!)

                        callback.onResult(reviewsListing.crew,null)
                        networkState.postValue(NetworkState.LOADED)
                        afterParams = null

                    }

                    else {
                        Log.d("MOVIZ-A","Error failed ${response.code()}")
                    }

                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    networkState.postValue(NetworkState.FAILED)
                    Log.d("MOVIZ-A","Error after ${t.message}")

                }

            })
    }


    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, CreditResponse.Crew>) {

    }

}
