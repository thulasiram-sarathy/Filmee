package com.thul.filmee.datasource

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.thul.filmee.viewmodel.review.ReviewViewModel

class ReviewModelFactory(private val id:Int):ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ReviewViewModel(id) as T
    }
}