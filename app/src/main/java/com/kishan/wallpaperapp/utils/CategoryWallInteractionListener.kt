package com.kishan.wallpaperapp.utils

import android.view.View
import com.kishan.wallpaperapp.model.Category

interface CategoryWallInteractionListener {
    fun onCategoryItemClick(category:Category, view:View)
}