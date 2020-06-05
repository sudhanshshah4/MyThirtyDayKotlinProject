package com.example.thirtydayskotlin.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.thirtydayskotlin.repository.Repository
import com.example.thirtydayskotlin.base.viewmodel.SearchListModel
import javax.inject.Inject

class ViewModelFactory @Inject constructor(private val repository: Repository):
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchListModel::class.java)) {
            return SearchListModel(repository) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}
