package com.kishan.wallpaperapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.kishan.wallpaperapp.R
import com.kishan.wallpaperapp.adapter.RecyclerViewAdapter
import com.kishan.wallpaperapp.basefragment.BaseFragment
import com.kishan.wallpaperapp.databinding.FragmentAllBinding
import com.kishan.wallpaperapp.model.Photo
import com.kishan.wallpaperapp.paging.loadinState.LoaderStateAdapter
import com.kishan.wallpaperapp.utils.Constants
import com.kishan.wallpaperapp.utils.WallInteractionListener
import com.kishan.wallpaperapp.viewmodel.CuratedViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class AllFragment : BaseFragment() {
    private val viewModel : CuratedViewModel by viewModels()
    override var recyclerViewAdapter: RecyclerViewAdapter = RecyclerViewAdapter(this)

    override fun initViewModel() {
        lifecycleScope.launch {
            viewModel.curatedPage.collectLatest {
                recyclerViewAdapter.submitData(it)
            }
        }
    }

//    override fun initRecyclerView() {
//        val layoutManager = GridLayoutManager(context ,2)
//        binding.curatedRecyclerView.layoutManager = layoutManager
//        binding.curatedRecyclerView.adapter = recyclerViewAdapter.withLoadStateHeaderAndFooter(
//            header = LoaderStateAdapter{recyclerViewAdapter.retry()},
//            footer = LoaderStateAdapter{recyclerViewAdapter.retry()}
//        )
//
//        recyclerViewAdapter.addLoadStateListener {loadState->
//            binding.curatedRecyclerView.isVisible = loadState.source.refresh is LoadState.NotLoading
//            binding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading
//            binding.buttonRetry.isVisible = loadState.source.refresh is LoadState.Error
//            handleError(loadState)
//        }
//
//        binding.buttonRetry.setOnClickListener {
//            recyclerViewAdapter.retry()
//        }
//    }

//    override fun onItemClick(photoData: Photo, view: View) {
//        val imageData = arrayOf(photoData.src.portrait)
//        Navigation.findNavController(view)
//            .navigate(
//                MainFragmentDirections.actionMainFragmentToDownloadFragment(
//                    imageData
//                )
//            )
//    }


}