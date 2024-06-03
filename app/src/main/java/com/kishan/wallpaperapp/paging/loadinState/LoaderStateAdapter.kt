package com.kishan.wallpaperapp.paging.loadinState

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter

class LoaderStateAdapter(
    private val retry:() -> Unit
) : LoadStateAdapter<LoadingStateViewHolder>(){
    override fun onBindViewHolder(holder: LoadingStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState,
    ): LoadingStateViewHolder {
       return LoadingStateViewHolder.create(parent ,retry)
    }
}