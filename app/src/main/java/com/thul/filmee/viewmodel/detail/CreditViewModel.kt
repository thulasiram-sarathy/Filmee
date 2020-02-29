package com.thul.filmee.viewmodel.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.thul.filmee.datasource.PageKeyCreditFactory
import com.thul.filmee.response.CreditResponse

import java.util.concurrent.Executor

class CreditViewModel(val id:Int): ViewModel()
{

    var castLiveData: LiveData<PagedList<CreditResponse.Cast>>
    private val pageSize = 25
    private val reviewsFactory: PageKeyCreditFactory = PageKeyCreditFactory( id, Executor {

    })

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(5)
            .setEnablePlaceholders(false)
            .build()

        castLiveData = LivePagedListBuilder(reviewsFactory,config).build()

    }







}