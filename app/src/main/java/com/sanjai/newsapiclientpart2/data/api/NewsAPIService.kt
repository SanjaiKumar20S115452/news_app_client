package com.sanjai.newsapiclientpart2.data.api

import com.sanjai.newsapiclientpart2.data.model.APIResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPIService {

    @GET("v2/top-headlines")
    suspend fun getNewsTopHeadlines(
        @Query("country")country: String,
        @Query("page")page: Int,
        @Query("apiKey")apiKey: String = "ba959ca60c03470f8ea1c9521147894a"
    ): Response<APIResponse>

    @GET("v2/top-headlines")
    suspend fun getSearchedNewsTopHeadlines(
        @Query("country")country: String,
        @Query("page")page: Int,
        @Query("q")searchQuery: String,
        @Query("apiKey")apiKey: String = "ba959ca60c03470f8ea1c9521147894a"
    ): Response<APIResponse>

}