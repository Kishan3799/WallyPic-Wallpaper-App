package com.kishan.wallypic.utils

import android.view.View
import com.kishan.wallypic.model.Category

interface CategoryWallInteractionListener {
    fun onCategoryItemClick(category:Category, view:View)
}