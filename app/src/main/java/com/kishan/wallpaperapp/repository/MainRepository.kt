package com.kishan.wallpaperapp.repository

import com.kishan.wallpaperapp.network.RetorfitApi
import com.kishan.wallpaperapp.network.RetrofitService

class MainRepository {
    fun retorfitService() : RetrofitService = RetorfitApi.apiService
}