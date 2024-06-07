package com.kishan.wallpaperapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.navigation.Navigation
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
        binding.toolbar.title = "WallyPic"
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)

        val menuHost : MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu, menu)

            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.search -> {
                        Navigation.findNavController(requireView())
                            .navigate(
                                MainFragmentDirections.actionMainFragmentToSearchFragment()
                            )
                        true
                    }
                    else -> false
                }
            }
        },viewLifecycleOwner, Lifecycle.State.RESUMED)
    }


}