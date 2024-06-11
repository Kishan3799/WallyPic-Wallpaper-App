package com.kishan.wallypic.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetorfitApi {

    private const val BASE_URL = "https://api.pexels.com/v1/"
    private val retorfit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService :RetrofitService = retorfit.create(
        RetrofitService::class.java
    )
}