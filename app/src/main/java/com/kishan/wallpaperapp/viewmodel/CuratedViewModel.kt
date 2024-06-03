package com.kishan.wallpaperapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.kishan.wallpaperapp.paging.CuratedPagingSource
import com.kishan.wallpaperapp.repository.MainRepository

class CuratedViewModel : ViewModel() {
    private val repository = MainRepository()

    val curatedPage = Pager(config = PagingConfig(pageSize = 30),
        pagingSourceFactory = {
            CuratedPagingSource(repository.retorfitService())
        }).flow.cachedIn(viewModelScope)
}