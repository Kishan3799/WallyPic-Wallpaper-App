package com.kishan.wallpaperapp.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.kishan.wallpaperapp.adapter.RecyclerViewAdapter
import com.kishan.wallpaperapp.databinding.FragmentSpecificCategoryBinding
import com.kishan.wallpaperapp.model.Photo
import com.kishan.wallpaperapp.paging.loadinState.LoaderStateAdapter
import com.kishan.wallpaperapp.utils.WallInteractionListener
import com.kishan.wallpaperapp.viewmodel.CategoryViewModel
import com.kishan.wallpaperapp.viewmodel.CategoryViewModelFactory
import kotlinx.coroutines.launch

class SpecificCategoryFragment : Fragment(), WallInteractionListener {
    private lateinit var viewModel:CategoryViewModel
    private val args : SpecificCategoryFragmentArgs by navArgs()
    private lateinit var binding : FragmentSpecificCategoryBinding
    private var recyclerViewAdapter : RecyclerViewAdapter =  RecyclerViewAdapter(this)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSpecificCategoryBinding.inflate(inflater, container, false)

        initViewModel()
        callBack()
        initRecyclerView()
        categoryName()
        return binding.root
    }

    private fun categoryName(){
        binding.categoryName.text = args.categoryName
    }
    private fun initRecyclerView(){
        val layoutManager = GridLayoutManager(context, 2)
        binding.categoryRecyclerView.layoutManager = layoutManager
        binding.categoryRecyclerView.adapter = recyclerViewAdapter
            .withLoadStateHeaderAndFooter(
                header = LoaderStateAdapter{recyclerViewAdapter.retry()},
                footer = LoaderStateAdapter{recyclerViewAdapter.retry()}
            )

        recyclerViewAdapter.addLoadStateListener {loadState->
            binding.categoryRecyclerView.isVisible = loadState.source.refresh is LoadState.NotLoading
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

    private fun callBack(){
        binding.categoryName.onRightDrawableClicked{
            Navigation.findNavController(it).popBackStack()
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun TextView.onRightDrawableClicked(onClicked: (view:TextView) -> Unit){
        this.setOnTouchListener { view, motionEvent ->
            var hasConsumend = false
            if(view is TextView){
                if(motionEvent.x >= (view.width - view.totalPaddingRight)){
                    if(motionEvent.action == MotionEvent.ACTION_UP){
                        onClicked(this)
                    }
                    hasConsumend = true
                }
            }
            hasConsumend
        }
    }
    private fun initViewModel(){
        viewModel = ViewModelProvider(
            this,
            CategoryViewModelFactory(args.categoryName)
        )[CategoryViewModel::class.java]
        viewModel.photoData.observe(viewLifecycleOwner){
            lifecycleScope.launch {
                recyclerViewAdapter.submitData(it)
            }
        }
    }


    override fun onItemClick(photoData: Photo, view: View) {
        val imageData = arrayOf(photoData.src.portrait)

        Navigation.findNavController(view).navigate(
            SpecificCategoryFragmentDirections.actionSpecificCategoryFragmentToDownloadFragment(
                imageData
            )
        )
    }
}