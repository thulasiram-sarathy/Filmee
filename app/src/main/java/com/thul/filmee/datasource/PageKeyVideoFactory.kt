package com.example.moviz.datasource

import androidx.lifecycle.MutableLiveData
import java.util.concurrent.Executor
import androidx.paging.DataSource
import com.thul.filmee.response.VideoResponse


class PageKeyVideoFactory(val type:Int, val retryExecutor:Executor):DataSource.Factory<Int, VideoResponse.Video>() {

    val popularMoviesMutableData = MutableLiveData<PageKeyedVideoDataSource>()
    override fun create(): DataSource<Int, VideoResponse.Video> {
        val moviesDataSource = PageKeyedVideoDataSource(type,retryExecutor)
        popularMoviesMutableData.postValue(moviesDataSource)
        return moviesDataSource
    }

}
