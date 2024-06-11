package com.kishan.wallypic.repository

import com.kishan.wallypic.network.RetorfitApi
import com.kishan.wallypic.network.RetrofitService

class MainRepository {
    fun retorfitService() : RetrofitService = RetorfitApi.apiService
}