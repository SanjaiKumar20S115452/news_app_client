package com.sanjai.newsapiclientpart2.data.repository.datasource

import com.sanjai.newsapiclientpart2.data.model.APIResponse
import retrofit2.Response

interface NewsRemoteDataSource {
    suspend fun getNewsTopHeadlines(country: String,page: Int): Response<APIResponse>
    suspend fun getSearchedNewsTopHeadlines(country: String,page: Int,searchQuery: String): Response<APIResponse>
}