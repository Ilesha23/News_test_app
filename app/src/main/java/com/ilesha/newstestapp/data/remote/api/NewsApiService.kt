package com.ilesha.newstestapp.data.remote.api

import com.ilesha.newstestapp.BuildConfig
import com.ilesha.newstestapp.data.remote.dto.NewsResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

    @GET("v2/everything")
    suspend fun searchNews(
        @Query("q") query: String,
        @Query("pageSize") pageSize: Int = ApiConstants.PAGE_SIZE,
        @Query("page") page: Int,
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY
    ): NewsResponseDto

    @GET("v2/top-headlines")
    suspend fun getNewsByCategory(
        @Query("category") category: String,
        @Query("pageSize") pageSize: Int = ApiConstants.PAGE_SIZE,
        @Query("page") page: Int,
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY
    ): NewsResponseDto

}