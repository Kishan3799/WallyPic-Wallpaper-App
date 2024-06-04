package com.kishan.wallpaperapp.network

import com.kishan.wallpaperapp.model.Wallpapers
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface RetrofitService {

    @Headers("Authorization: IW0QGbJm8r2yKigHSEenUgH0xE0W8lOQnvXMaoZjAxjpn1uYMRb893fp")
    @GET("curated")
    suspend fun getCuratedWallPaper(@Query("page") page:Int) : Wallpapers


    @Headers("Authorization: IW0QGbJm8r2yKigHSEenUgH0xE0W8lOQnvXMaoZjAxjpn1uYMRb893fp")
    @GET("search")
    suspend fun searchWallPaper(
        @Query("page") page: Int,
        @Query("query") query:String
    ) : Wallpapers

    @Headers("Authorization: IW0QGbJm8r2yKigHSEenUgH0xE0W8lOQnvXMaoZjAxjpn1uYMRb893fp")
    @GET("search")
    suspend fun wallPaperCategory(
        @Query("page") page: Int,
        @Query("query") category:String
    ) : Wallpapers

}