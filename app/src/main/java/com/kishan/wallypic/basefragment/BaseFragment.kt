package com.kishan.wallypic.basefragment

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
import com.kishan.wallypic.adapter.RecyclerViewAdapter
import com.kishan.wallypic.databinding.FragmentAllBinding
import com.kishan.wallypic.fragment.MainFragmentDirections
import com.kishan.wallypic.model.Photo
import com.kishan.wallypic.paging.loadinState.LoaderStateAdapter
import com.kishan.wallypic.utils.WallInteractionListener

abstract class BaseFragment : Fragment(), WallInteractionListener {
    private lateinit var binding: FragmentAllBinding
    abstract var recyclerViewAdapter : RecyclerViewAdapter

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


    private fun handleError(loadState: CombinedLoadStates){
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