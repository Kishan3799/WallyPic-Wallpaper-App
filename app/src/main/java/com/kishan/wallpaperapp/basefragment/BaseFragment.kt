package com.kishan.wallpaperapp.basefragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.viewbinding.ViewBinding
import com.kishan.wallpaperapp.adapter.RecyclerViewAdapter

abstract class BaseFragment <VB : ViewBinding> (
    private val layoutInflater: (inflator:LayoutInflater) -> VB
) : Fragment(){
    abstract var recyclerViewAdapter : RecyclerViewAdapter
    private var _binding: VB? = null

    val binding: VB
        get() = _binding as VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = layoutInflater.invoke(inflater)

        if (_binding == null){
            throw IllegalArgumentException("binding cannot be null")
        }
        initViewModel()
        initRecyclerView()
        return binding.root
    }

    abstract fun initViewModel()
    abstract fun initRecyclerView()


    fun handleError(loadState: CombinedLoadStates){
        val errorState = loadState.source.append as? LoadState.Error
            ?: loadState.source.prepend as? LoadState.Error

        errorState?.let {
            Toast.makeText(context, "Try again later", Toast.LENGTH_LONG).show()
        }
    }
}