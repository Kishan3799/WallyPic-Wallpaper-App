package com.kishan.wallpaperapp.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RetorfitApi {

    private val BASE_URL = "https://api.pexels.com/v1/"
    private val retorfit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService :RetrofitService = retorfit.create(
        RetrofitService::class.java
    )
}