package com.kishan.wallypic.utils

import android.view.View
import com.kishan.wallypic.model.Photo

interface WallInteractionListener {
    fun onItemClick(photoData:Photo, view: View)
}