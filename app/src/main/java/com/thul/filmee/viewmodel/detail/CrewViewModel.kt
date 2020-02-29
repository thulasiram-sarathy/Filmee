package com.thul.filmee.viewmodel.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.thul.filmee.datasource.PageKeyCrewFactory
import com.thul.filmee.response.CreditResponse

import java.util.concurrent.Executor

class CrewViewModel(val id:Int): ViewModel()
{

    var crewLiveData: LiveData<PagedList<CreditResponse.Crew>>
    private val pageSize = 25
    private val reviewsFactory: PageKeyCrewFactory = PageKeyCrewFactory( id, Executor {

    })

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(5)
            .setEnablePlaceholders(false)
            .build()

        crewLiveData = LivePagedListBuilder(reviewsFactory,config).build()

    }







}