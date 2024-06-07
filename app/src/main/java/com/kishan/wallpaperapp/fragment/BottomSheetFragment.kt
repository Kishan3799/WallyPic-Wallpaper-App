package com.kishan.wallpaperapp.fragment

import android.app.DownloadManager
import android.app.WallpaperManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.imageview.ShapeableImageView
import com.kishan.wallpaperapp.R
import com.kishan.wallpaperapp.databinding.BottomSheetBinding
import com.kishan.wallpaperapp.utils.Constants
import java.io.File
import java.io.IOException

class BottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding:BottomSheetBinding
    private var wallUrl : String? = null

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
    ): View? {
        binding = BottomSheetBinding.inflate(inflater)
        initButtons()
        return binding.root
    }

    private fun initButtons() {
        binding.downloadFromNet.setOnClickListener{
            wallUrl?.let { it1 -> downloadFromNet(it1) }
        }
        binding.setAsHomeScreen.setOnClickListener {
            setAsHomeScreen(Constants.background.homeScreen)
        }

        binding.setAsLockScreen.setOnClickListener {
            setAsHomeScreen(Constants.background.lockScreen)
        }

    }

    private fun downloadFromNet(url:String){
        try{
            val downlaodManger = context?.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

            val imageUrl = Uri.parse(url)
            val request = DownloadManager.Request(imageUrl).apply {
                setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
                    .setMimeType("image/*")
                    .setAllowedOverRoaming(false)
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_ONLY_COMPLETION)
                    .setTitle("Wall")
                    .setDestinationInExternalPublicDir(
                        Environment.DIRECTORY_PICTURES,
                        File.separator + "wall" + ".jpg"
                    )
            }
            downlaodManger.enqueue(request)
            Toast.makeText(context,"Downloading...", Toast.LENGTH_LONG).show()
        }catch (e:Exception){
            Toast.makeText(context, "Downloading Failed ... ${e.message}" , Toast.LENGTH_LONG).show()
        }
    }


    private fun setAsHomeScreen(LockOrHome:Int){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            try {
                val wallpaperManager = WallpaperManager.getInstance(context)
                val image = activity?.findViewById<ShapeableImageView>(R.id.download_image_view)

                if (image?.drawable == null){
                    Toast.makeText(context,"wait to download", Toast.LENGTH_SHORT).show()
                }else{
                    val bitmap = (image.drawable as BitmapDrawable).bitmap
                    wallpaperManager.setBitmap(bitmap, null,true, LockOrHome)
                    Toast.makeText(context, "Done", Toast.LENGTH_SHORT).show()
                }
            }catch (e:IOException){
                Toast.makeText(context,e.message,Toast.LENGTH_LONG).show()
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