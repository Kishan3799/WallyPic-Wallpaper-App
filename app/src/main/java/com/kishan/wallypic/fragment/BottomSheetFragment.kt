package com.kishan.wallypic.fragment

import android.app.DownloadManager
import android.app.WallpaperManager
import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.imageview.ShapeableImageView
import com.kishan.wallypic.R
import com.kishan.wallypic.databinding.BottomSheetBinding
import com.kishan.wallypic.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.IOException

class BottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetBinding
    private var wallUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            wallUrl = it.getString(ARG_WALL_URL)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomSheetBinding.inflate(inflater, container, false)
        initButtons()
        return binding.root
    }

    private fun initButtons() {
        binding.downloadFromNet.setOnClickListener {
            wallUrl?.let { url -> downloadFromNet(url) }
            dismiss()
        }
        binding.setAsHomeScreen.setOnClickListener {
            setAsWallpaper(WallpaperManager.FLAG_SYSTEM)
            dismiss()
        }

        binding.setAsLockScreen.setOnClickListener {
            setAsWallpaper(WallpaperManager.FLAG_LOCK)
            dismiss()
        }
    }

    private fun downloadFromNet(url: String) {
        lifecycleScope.launch {
            try {
                val downloadManager = context?.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
                val imageUrl = Uri.parse(url)
                val request = DownloadManager.Request(imageUrl).apply {
                    setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
                    setMimeType("image/*")
                    setAllowedOverRoaming(false)
                    setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_ONLY_COMPLETION)
                    setTitle("WallyPic")
                    setDestinationInExternalPublicDir(
                        Environment.DIRECTORY_PICTURES,
                        File.separator + "wallypic" + ".jpg"
                    )
                }
                downloadManager.enqueue(request)
                Toast.makeText(context, "Downloading...", Toast.LENGTH_LONG).show()
            } catch (e: Exception) {
                Toast.makeText(context, "Downloading Failed ... ${e.message}", Toast.LENGTH_LONG).show()
            }
        }

    }

    private fun setAsWallpaper(flag: Int) {
        lifecycleScope.launch {
            try {
                val wallpaperManager = WallpaperManager.getInstance(context)
                val image = activity?.findViewById<ShapeableImageView>(R.id.download_image_view)

                if (image?.drawable == null) {
                    Toast.makeText(context, "Wait for download to complete", Toast.LENGTH_SHORT).show()
                    return@launch
                }

                val bitmap = (image.drawable as BitmapDrawable).bitmap
                wallpaperManager.setBitmap(bitmap, null, true, flag)
                Toast.makeText(context, "Wallpaper set successfully", Toast.LENGTH_SHORT).show()
            } catch (e: IOException) {
                Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
            }
        }

    }

    companion object {
        private const val ARG_WALL_URL = "wall_url"

        @JvmStatic
        fun newInstance(wallUrl: String) =
            BottomSheetFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_WALL_URL, wallUrl)
                }
            }
    }
}