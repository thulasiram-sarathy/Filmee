package com.example.moviz.datasource

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.an.github.rest.RestApi
import com.thul.filmee.AppConstants.Companion.TMDB_KEY
import com.thul.filmee.datasource.NetworkState
import com.thul.filmee.response.VideoResponse

import com.thul.filmee.utils.parseStringToVideo
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import java.util.concurrent.Executor


class PageKeyedVideoDataSource (
                                 val type:Int,
                                        retryExecutor: Executor
) : PageKeyedDataSource<Int, VideoResponse.Video>()

{

    val tmdbService: RestApi by lazy {
        RestApi.create()
    }

    var initialParams: LoadInitialParams<Int>? = null
    var afterParams: LoadParams<Int>? = null
    var retry: (() -> Any)? = null
    val networkState = MutableLiveData<NetworkState>()
    val initial = MutableLiveData<NetworkState>()


    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, VideoResponse.Video>) {
        networkState.postValue(NetworkState.LOADING)
        initial.postValue(NetworkState.LOADING)
        tmdbService.getVideos(type,TMDB_KEY)
            .enqueue(object : retrofit2.Callback<ResponseBody> {



                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if (response.isSuccessful && response.code() == 200) {

                        val moviesResponseString = response.body()?.string()
                        Log.d("RESPONSE-",response.body().toString())
                        val videoListingData = parseStringToVideo(moviesResponseString!!)
                        

                        Log.d("MOVIZ-A",response.body().toString())
                        callback.onResult(videoListingData.results,null,2)
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

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, VideoResponse.Video>) {
        afterParams = params
        networkState.postValue(NetworkState.LOADING)
        tmdbService.getVideos(type, TMDB_KEY)
            .enqueue(object : retrofit2.Callback<ResponseBody> {

                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

                    if (response.isSuccessful && response.code() == 200) {

                        val moviesResponseString = response.body()?.string()
                        val moviesListingData = parseStringToVideo(moviesResponseString!!)

//                        callback.onResult(moviesListingData.results,moviesListingData.page+1)
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


    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, VideoResponse.Video>) {

    }

}
