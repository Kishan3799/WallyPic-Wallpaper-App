package com.kishan.wallypic.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.kishan.wallypic.model.Photo
import com.kishan.wallypic.paging.SearchPagingSource
import com.kishan.wallypic.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class SearchViewModel(private var searchQuery:String) : ViewModel() {
    private val repository = MainRepository()

    var photoData : MutableLiveData<PagingData<Photo>> = MutableLiveData()

    init {
        performSearch(searchQuery)
    }

    fun search(newQuery:String){
        searchQuery = newQuery
        performSearch(searchQuery)
    }

    private fun performSearch(search:String){
        viewModelScope.launch {
            loadPhotoDataOnSearch(search).collect{
                photoData.postValue(it)
            }
        }
    }
    private fun loadPhotoDataOnSearch(search:String) : Flow<PagingData<Photo>> {
        return Pager(
            config = PagingConfig(pageSize = 30),
            pagingSourceFactory = {
                SearchPagingSource(repository.retorfitService(), search)
            }
        ).flow.cachedIn(viewModelScope)
    }



}