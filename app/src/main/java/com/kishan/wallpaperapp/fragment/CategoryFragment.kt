package com.kishan.wallpaperapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.kishan.wallpaperapp.R
import com.kishan.wallpaperapp.adapter.CategoryAdapter
import com.kishan.wallpaperapp.adapter.RecyclerViewAdapter
import com.kishan.wallpaperapp.basefragment.BaseFragment
import com.kishan.wallpaperapp.databinding.FragmentCategoryBinding
import com.kishan.wallpaperapp.utils.ApiListCategory


class CategoryFragment : Fragment(){

    private lateinit var binding: FragmentCategoryBinding
    private lateinit var categoryAdapter:CategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoryBinding.inflate(inflater,container, false)
        initRecyclerViewAdapter()
        return binding.root
    }

    private fun initRecyclerViewAdapter() {
        val layoutManager = GridLayoutManager(context, 2)
        categoryAdapter = CategoryAdapter(ApiListCategory.list)

        binding.categoryRecyclerView.layoutManager = layoutManager
        binding.categoryRecyclerView.adapter = categoryAdapter
    }


}