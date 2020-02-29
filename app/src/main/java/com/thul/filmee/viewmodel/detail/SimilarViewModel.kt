package com.thul.filmee.viewmodel.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.moviz.datasource.PageKeySimilarMoviesFactory
import com.thul.filmee.response.MovieApiResponse

import java.util.concurrent.Executor

class SimilarViewModel(val id:Int): ViewModel()
{

    var similarLiveData: LiveData<PagedList<MovieApiResponse>>
    private val pageSize = 15
    private val reviewsFactory: PageKeySimilarMoviesFactory = PageKeySimilarMoviesFactory( id, Executor {

    })

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(5)
            .setEnablePlaceholders(false)
            .build()

        similarLiveData = LivePagedListBuilder(reviewsFactory,config).build()

    }







}