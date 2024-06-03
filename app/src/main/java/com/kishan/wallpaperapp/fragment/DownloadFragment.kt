package com.kishan.wallpaperapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.kishan.wallpaperapp.R
import com.kishan.wallpaperapp.databinding.FragmentDownloadBinding


class DownloadFragment : Fragment() {
    private lateinit var binding : FragmentDownloadBinding
    private val args : DownloadFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentDownloadBinding.inflate(inflater)
        loadImage(args.imageData[0])
        addCallBack()
        bottomSheet()
        return binding.root
    }

    private fun loadImage(url:String){
        Glide.with(this)
            .load(url)
            .centerCrop()
            .into(binding.downloadImageView)

    }

    private fun addCallBack(){
        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun bottomSheet(){
        val bottomSheet = BottomSheetFragment(args.imageData[0])
        binding.downlaodFabButton.setOnClickListener {
            bottomSheet.show(requireActivity().supportFragmentManager, "bottomSheet")
        }
    }

}