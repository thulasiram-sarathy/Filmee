package com.thul.filmee.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.thul.filmee.response.CreditResponse

import java.util.concurrent.Executor

class PageKeyCreditFactory(val id:Int, val retryExecutor: Executor):
    DataSource.Factory<Int, CreditResponse.Cast>() {

    val reviewsMutableLiveData = MutableLiveData<PageKeyedCreditDataSource>()
    override fun create(): DataSource<Int, CreditResponse.Cast> {
        val reviewsDataSource = PageKeyedCreditDataSource(id,retryExecutor)
        reviewsMutableLiveData.postValue(reviewsDataSource)
        return reviewsDataSource
    }

}