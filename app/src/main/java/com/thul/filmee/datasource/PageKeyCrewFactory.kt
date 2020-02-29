package com.thul.filmee.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.thul.filmee.response.CreditResponse

import java.util.concurrent.Executor

class PageKeyCrewFactory(val id:Int, val retryExecutor: Executor):
    DataSource.Factory<Int, CreditResponse.Crew>() {

    val crewMutableLiveData = MutableLiveData<PageKeyedCrewDataSource>()
    override fun create(): DataSource<Int, CreditResponse.Crew> {
        val reviewsDataSource = PageKeyedCrewDataSource(id,retryExecutor)
        crewMutableLiveData.postValue(reviewsDataSource)
        return reviewsDataSource
    }

}