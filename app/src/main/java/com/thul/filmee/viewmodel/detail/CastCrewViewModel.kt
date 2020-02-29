package com.thul.filmee.viewmodel.detail

import android.app.Application

import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.an.github.rest.RestApi
import com.thul.filmee.AppConstants
import com.thul.filmee.response.CreditResponse
import com.thul.filmee.utils.parseCastCrewData
import okhttp3.ResponseBody

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.collections.ArrayList

class CastCrewViewModel(application: Application) : AndroidViewModel(application) {

    val tmdbService: RestApi by lazy {
        RestApi.create()
    }

    lateinit var dataList:ArrayList<CreditResponse>
    private val mutablePostList: MutableLiveData<List<CreditResponse>> = MutableLiveData()



    fun getCastCrewService(movieid: Int): LiveData<List<CreditResponse>>? {
        dataList = ArrayList()

        val call = tmdbService.getCastCrew(movieid, AppConstants.TMDB_KEY)

        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful && response.code() == 200){
                    val castcrewResponseString = response.body()?.string()
                    val castcrewListing = parseCastCrewData(castcrewResponseString!!)
                    castcrewListing.cast
                    dataList.add(castcrewListing)
                    mutablePostList.postValue(dataList)
                }


            }

            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                Log.d("ERROR : ", t.toString())

            }
        })

        return mutablePostList
    }



}