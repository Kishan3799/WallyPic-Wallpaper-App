package com.kishan.wallpaperapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.kishan.wallpaperapp.R
import com.kishan.wallpaperapp.adapter.ViewPagerAdapter
import com.kishan.wallpaperapp.databinding.FragmentMainBinding


class MainFragment : Fragment() {

    private val tabTiles = listOf("All", "Category")
    private lateinit var binding:FragmentMainBinding
    private val fragment = listOf(AllFragment(), CategoryFragment())



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        initToolbar()
        initViewPager()
        initTabLayout()
        return binding.root
    }

    private fun initTabLayout(){
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, index->
            tab.text = tabTiles[index]
        }.attach()
    }

    private fun initViewPager(){
        val pagerAdapter = ViewPagerAdapter(context as FragmentActivity, fragment)
        binding.viewPager.adapter = pagerAdapter
        binding.viewPager.isUserInputEnabled = false
    }
    private fun initToolbar() {
        binding.toolbar.title = "Wallpapers"
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
    }

}