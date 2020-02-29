package com.thul.filmee.viewmodel.review

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.thul.filmee.datasource.PageKeyReviewsFactory
import com.thul.filmee.response.ReviewsResponse

import java.util.concurrent.Executor

class ReviewViewModel(val id:Int): ViewModel()
{

    var reviewsLiveData: LiveData<PagedList<ReviewsResponse.Review>>
    private val pageSize = 25
    private val reviewsFactory: PageKeyReviewsFactory = PageKeyReviewsFactory( id, Executor {

    })

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(5)
            .setEnablePlaceholders(false)
            .build()

        reviewsLiveData = LivePagedListBuilder(reviewsFactory,config).build()

    }







}