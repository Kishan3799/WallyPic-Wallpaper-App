package com.kishan.wallpaperapp.basefragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewbinding.ViewBinding
import com.kishan.wallpaperapp.adapter.RecyclerViewAdapter
import com.kishan.wallpaperapp.databinding.FragmentAllBinding
import com.kishan.wallpaperapp.fragment.MainFragmentDirections
import com.kishan.wallpaperapp.model.Photo
import com.kishan.wallpaperapp.paging.loadinState.LoaderStateAdapter
import com.kishan.wallpaperapp.utils.WallInteractionListener

abstract class BaseFragment : Fragment(), WallInteractionListener {
    private lateinit var binding: FragmentAllBinding
    abstract var recyclerViewAdapter : RecyclerViewAdapter
//    private var _binding: VB? = null

//    val binding: VB
//        get() = _binding as VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAllBinding.inflate(inflater,container,false)

        initViewModel()
        recyclerView()
        return binding.root
    }

    abstract fun initViewModel()
    fun recyclerView(){
        val layoutManager = GridLayoutManager(context ,2)
        binding.curatedRecyclerView.layoutManager = layoutManager
        binding.curatedRecyclerView.adapter = recyclerViewAdapter.withLoadStateHeaderAndFooter(
            header = LoaderStateAdapter{recyclerViewAdapter.retry()},
            footer = LoaderStateAdapter{recyclerViewAdapter.retry()}
        )

        recyclerViewAdapter.addLoadStateListener {loadState->
            binding.curatedRecyclerView.isVisible = loadState.source.refresh is LoadState.NotLoading
            binding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading
            binding.buttonRetry.isVisible = loadState.source.refresh is LoadState.Error
            handleError(loadState)
        }

        binding.buttonRetry.setOnClickListener {
            recyclerViewAdapter.retry()
        }
    }


    fun handleError(loadState: CombinedLoadStates){
        val errorState = loadState.source.append as? LoadState.Error
            ?: loadState.source.prepend as? LoadState.Error

        errorState?.let {
            Toast.makeText(context, "Try again later", Toast.LENGTH_LONG).show()
        }
    }

    override fun onItemClick(photoData: Photo, view: View) {
        val imageData = arrayOf(photoData.src.portrait)
        Navigation.findNavController(view)
            .navigate(
                MainFragmentDirections.actionMainFragmentToDownloadFragment(
                    imageData
                )
            )
    }
}