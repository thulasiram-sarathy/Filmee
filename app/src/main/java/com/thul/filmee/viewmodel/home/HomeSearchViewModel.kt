package com.thul.filmee.viewmodel.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.moviz.datasource.PageKeySearchFactory
import com.thul.filmee.response.MovieApiResponse
import java.util.concurrent.Executor


class HomeSearchViewModel(search : String): ViewModel()
{

    var searchMoviesLiveData: LiveData<PagedList<MovieApiResponse>>
    private val pageSize = 10
    private val searchMoviesFactory: PageKeySearchFactory = PageKeySearchFactory(search, Executor {

    })

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(5)
            .setEnablePlaceholders(false)
            .build()
        searchMoviesLiveData = LivePagedListBuilder(searchMoviesFactory,config).build()

    }







}
