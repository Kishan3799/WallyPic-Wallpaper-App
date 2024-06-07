package com.kishan.wallpaperapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.kishan.wallpaperapp.R
import com.kishan.wallpaperapp.adapter.RecyclerViewAdapter
import com.kishan.wallpaperapp.databinding.FragmentSearchBinding
import com.kishan.wallpaperapp.model.Photo
import com.kishan.wallpaperapp.paging.loadinState.LoaderStateAdapter
import com.kishan.wallpaperapp.utils.WallInteractionListener
import com.kishan.wallpaperapp.viewmodel.SearchViewModel
import com.kishan.wallpaperapp.viewmodel.SearchViewModelFactory
import kotlinx.coroutines.launch


class SearchFragment : Fragment(), WallInteractionListener {
    private lateinit var viewModel: SearchViewModel
    private lateinit var  binding:FragmentSearchBinding
    private var recyclerViewAdapter : RecyclerViewAdapter = RecyclerViewAdapter(this)
    private var search :String =""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater)
        binding.searchIconButton.setOnClickListener {
            performSearch()
        }

        binding.searchEditText.setOnEditorActionListener{_,actionId,_ ->
            if(actionId == EditorInfo.IME_ACTION_SEARCH) {
                performSearch()
                true
            }else{
                false
            }
        }

        initViewModel()
        initRecyclerView()

        return binding.root
    }

    private fun performSearch(){
        val query = binding.searchEditText.text.toString().trim()
        if(query.isNotEmpty()){
            viewModel.search(query)
        }
    }




    private fun initRecyclerView(){
        val layoutManager = GridLayoutManager(context, 2)
        binding.searchRecyclerView.layoutManager = layoutManager
        binding.searchRecyclerView.adapter = recyclerViewAdapter
            .withLoadStateHeaderAndFooter(
                header = LoaderStateAdapter{recyclerViewAdapter.retry()},
                footer = LoaderStateAdapter{recyclerViewAdapter.retry()}
            )

        recyclerViewAdapter.addLoadStateListener { loadState->
            binding.searchRecyclerView.isVisible = loadState.source.refresh is LoadState.NotLoading
            binding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading
            binding.buttonRetry.isVisible = loadState.source.refresh is LoadState.Error
            handleError(loadState)
        }
    }

    private fun handleError(loadState: CombinedLoadStates){
        val errorState = loadState.source.append as? LoadState.Error ?: loadState.source.prepend as? LoadState.Error

        errorState?.let {
            Toast.makeText(context,"Try again later", Toast.LENGTH_LONG).show()
        }
    }
    private fun initViewModel(){
        viewModel = ViewModelProvider(
            this,
            SearchViewModelFactory(search)
        )[SearchViewModel::class.java]
        viewModel.photoData.observe(viewLifecycleOwner){
            lifecycleScope.launch {
                recyclerViewAdapter.submitData(it)
            }
        }
    }
    override fun onItemClick(photoData: Photo, view: View) {
        val imageData = arrayOf(photoData.src.original)

        Navigation.findNavController(view).navigate(
            SearchFragmentDirections.actionSearchFragmentToDownloadFragment(
                imageData
            )
        )
    }


}