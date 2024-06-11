package com.kishan.wallypic.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Suppress("UNCHECKED_CAST")
class CategoryViewModelFactory(private val categoryId:String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CategoryViewModel::class.java)){
            return CategoryViewModel(categoryId) as T
        }
        throw IllegalArgumentException("Unknown viewModel class")
    }
}