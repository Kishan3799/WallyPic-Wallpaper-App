package com.kishan.wallpaperapp.paging.loadinState

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.kishan.wallpaperapp.R
import com.kishan.wallpaperapp.databinding.ItemLoaderBinding

class LoadingStateViewHolder(
    private val binding : ItemLoaderBinding,
    retry: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.retryButtonItem.setOnClickListener {
            retry.invoke()
        }
    }
    fun bind(loadState: LoadState) {
        if (loadState is LoadState.Error) {
            binding.errorMsg.text = "Please retyr again later"
        }
        binding.progressBar.isVisible= loadState is LoadState.Loading
        binding.retryButtonItem.isVisible = loadState !is LoadState.Loading
        binding.errorMsg.isVisible = loadState !is LoadState.Loading
    }

    companion object {
        fun create(parent:ViewGroup, retry: () -> Unit): LoadingStateViewHolder{
            val view  = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_loader,parent,false)
            val binding = ItemLoaderBinding.bind(view)
            return LoadingStateViewHolder(binding, retry)
        }
    }
}