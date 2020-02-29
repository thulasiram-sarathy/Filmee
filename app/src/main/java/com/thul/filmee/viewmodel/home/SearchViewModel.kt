package com.thul.filmee.viewmodel.home

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.an.github.rest.RestApi
import com.thul.filmee.AppConstants.Companion.TMDB_KEY
import com.thul.filmee.api.RxSingleSchedulers
import com.thul.filmee.response.SearchResponse
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


class SearchViewModel() : ViewModel() {

    var disposable = CompositeDisposable()
    private var sortDispoable: Disposable? = null
    private val tmdbService:RestApi by lazy {
        RestApi.create()
    }
    private val apiData: MutableLiveData<SearchResponse> = MutableLiveData()
    private val getMovieLiveData: MutableLiveData<List<SearchResponse.MovieApiResponse>> = MutableLiveData()
    private val apiError = MutableLiveData<Throwable>()
    private val loading = MutableLiveData<Boolean>()

    fun apiTweetData(): MutableLiveData<SearchResponse> {
        return apiData
    }

    fun apiMovieData(): MutableLiveData<List<SearchResponse.MovieApiResponse>> {
        return getMovieLiveData
    }

    fun apiError(): MutableLiveData<Throwable> {
        return apiError
    }

    fun loading(): MutableLiveData<Boolean> {
        return loading
    }

    @SuppressLint("CheckResult")
    fun fetchMoviesList(query: String){
            val observable = tmdbService.getMoviesList(query,TMDB_KEY,  1)
            observable?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.compose(RxSingleSchedulers.DEFAULT.applySchedulers<SearchResponse>())
                ?.subscribe({ loginResponse ->
//                    UtilMethods.hideLoading()
                    apiData.postValue(loginResponse)

                }, { error ->
                    Log.v("fetchMoviesList", " "+ error.localizedMessage)
//                    UtilMethods.hideLoading()
//                    UtilMethods.showLongToast(mContext, error.message.toString())
                }
                )

    }

    @SuppressLint("CheckResult")
    fun fetchMovie(){
        val observable = Observable.just(apiData.value?.results)
        observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { loginResponse ->
                getMovieLiveData.postValue(loginResponse)
            }
    }


    override fun onCleared() {
        super.onCleared()
        if (disposable != null) {
            disposable!!.clear()
//            disposable = null
        }
        if (sortDispoable != null) {
            sortDispoable!!.dispose()
            sortDispoable = null
        }
    }



}


