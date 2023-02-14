package com.sanjai.newsapiclientpart2.domain.repository

import com.sanjai.newsapiclientpart2.data.model.APIResponse
import com.sanjai.newsapiclientpart2.data.model.Article
import com.sanjai.newsapiclientpart2.data.util.Resource
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    //NETWORK RELATED
    suspend fun getNewsHeadlines(country: String,page: Int): Resource<APIResponse>
    suspend fun getSearchedNews(country: String,page: Int,searchQuery: String): Resource<APIResponse>

    //DATABASE FUNCTIONS
    suspend fun saveNews(article: Article)
    suspend fun deleteNews(article: Article)
    fun getSavedNews(): Flow<List<Article>>

}