package com.example.moviz.datasource

import androidx.lifecycle.MutableLiveData
import java.util.concurrent.Executor
import androidx.paging.DataSource
import com.thul.filmee.response.MovieApiResponse


class PageKeySimilarMoviesFactory(val type:Int, val retryExecutor:Executor):DataSource.Factory<Int, MovieApiResponse>() {

    val popularMoviesMutableData = MutableLiveData<PageKeyedSimilarMoviesDataSource>()
    override fun create(): DataSource<Int, MovieApiResponse> {
        val moviesDataSource = PageKeyedSimilarMoviesDataSource(type,retryExecutor)
        popularMoviesMutableData.postValue(moviesDataSource)
        return moviesDataSource
    }

}
