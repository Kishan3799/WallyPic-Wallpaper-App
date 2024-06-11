package com.kishan.wallypic.network

import com.kishan.wallypic.BuildConfig
import com.kishan.wallypic.model.Wallpapers
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface RetrofitService {

    @Headers("Authorization: ${BuildConfig.API_KEY}")
    @GET("curated")
    suspend fun getCuratedWallPaper(@Query("page") page: Int): Wallpapers

    @Headers("Authorization: ${BuildConfig.API_KEY}")
    @GET("search")
    suspend fun searchWallPaper(
        @Query("page") page: Int,
        @Query("query") query: String
    ): Wallpapers

    @Headers("Authorization: ${BuildConfig.API_KEY}")
    @GET("search")
    suspend fun wallPaperCategory(
        @Query("page") page: Int,
        @Query("query") category: String
    ): Wallpapers
}