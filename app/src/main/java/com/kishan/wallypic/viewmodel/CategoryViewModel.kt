package com.kishan.wallypic.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.kishan.wallypic.model.Photo
import com.kishan.wallypic.paging.CategoryPagingSource
import com.kishan.wallypic.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class CategoryViewModel(private var categoryId:String): ViewModel() {
    private val repository = MainRepository()

    var photoData : MutableLiveData<PagingData<Photo>> = MutableLiveData()

    init {
        viewModelScope.launch {
            loadCategory(categoryId).collect{
                photoData.postValue(it)
            }
        }
    }

    private fun loadCategory(category: String): Flow<PagingData<Photo>> {
        return Pager(config = PagingConfig(pageSize = 30),
            pagingSourceFactory = { CategoryPagingSource(repository.retorfitService(), category)}
        ).flow.cachedIn(viewModelScope)
    }

}