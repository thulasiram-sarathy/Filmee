package com.thul.filmee.datasource

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.thul.filmee.viewmodel.detail.CrewViewModel

class CrewViewModelFactory(private val id:Int):ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CrewViewModel(id) as T
    }
}