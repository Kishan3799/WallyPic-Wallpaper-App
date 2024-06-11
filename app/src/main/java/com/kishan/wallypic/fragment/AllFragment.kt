package com.kishan.wallypic.fragment

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.kishan.wallypic.adapter.RecyclerViewAdapter
import com.kishan.wallypic.basefragment.BaseFragment
import com.kishan.wallypic.viewmodel.CuratedViewModel
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

}