package com.thul.filmee.viewmodel.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.moviz.datasource.PageKeyMoviesFactory
import com.thul.filmee.response.MovieApiResponse
import java.util.concurrent.Executor

class HomeViewModel: ViewModel()
{

    var popularMoviesLiveData: LiveData<PagedList<MovieApiResponse>>
    private val pageSize = 10
    private val popularMoviesFactory: PageKeyMoviesFactory = PageKeyMoviesFactory("popular", Executor {

    })

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(5)
            .setEnablePlaceholders(false)
            .build()

        popularMoviesLiveData = LivePagedListBuilder(popularMoviesFactory,config).build()

    }







}
