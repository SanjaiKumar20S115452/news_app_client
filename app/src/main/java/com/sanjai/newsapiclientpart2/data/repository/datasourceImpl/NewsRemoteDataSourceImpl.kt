package com.sanjai.newsapiclientpart2.data.repository.datasourceImpl

import com.sanjai.newsapiclientpart2.data.api.NewsAPIService
import com.sanjai.newsapiclientpart2.data.model.APIResponse
import com.sanjai.newsapiclientpart2.data.repository.datasource.NewsRemoteDataSource
import retrofit2.Response

class NewsRemoteDataSourceImpl(
    private val newsAPIService: NewsAPIService
): NewsRemoteDataSource {
    override suspend fun getNewsTopHeadlines(country: String,page: Int): Response<APIResponse> {
        return newsAPIService.getNewsTopHeadlines(country, page)
    }

    override suspend fun getSearchedNewsTopHeadlines(
        country: String,
        page: Int,
        searchQuery: String
    ): Response<APIResponse> {
        return newsAPIService.getSearchedNewsTopHeadlines(country, page, searchQuery)
    }

}