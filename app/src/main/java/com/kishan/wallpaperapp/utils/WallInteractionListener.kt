package com.kishan.wallpaperapp.utils

import android.view.View
import com.kishan.wallpaperapp.model.Photo

interface WallInteractionListener {
    fun onItemClick(photoData:Photo, view: View)
}