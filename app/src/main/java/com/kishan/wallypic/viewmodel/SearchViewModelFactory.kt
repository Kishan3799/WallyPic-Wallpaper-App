package com.kishan.wallypic.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SearchViewModelFactory(private val searchQuery: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return SearchViewModel(searchQuery) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}