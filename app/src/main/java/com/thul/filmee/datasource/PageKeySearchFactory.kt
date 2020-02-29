package com.example.moviz.datasource

import androidx.lifecycle.MutableLiveData
import java.util.concurrent.Executor
import androidx.paging.DataSource
import com.thul.filmee.response.MovieApiResponse


class PageKeySearchFactory(val type:String, val retryExecutor:Executor):DataSource.Factory<Int, MovieApiResponse>() {

    val searchMoviesMutableData = MutableLiveData<PageKeyedSearchDataSource>()
    override fun create(): DataSource<Int, MovieApiResponse> {
        val moviesDataSource = PageKeyedSearchDataSource(type,retryExecutor)
        searchMoviesMutableData.postValue(moviesDataSource)
        return moviesDataSource
    }

}
