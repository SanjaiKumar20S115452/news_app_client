package com.sanjai.newsapiclientpart2.data.repository.datasource

import com.sanjai.newsapiclientpart2.data.model.Article
import kotlinx.coroutines.flow.Flow

interface NewsLocalDataSource {
    suspend fun saveArticles(article: Article)
    fun getSavedArticles(): Flow<List<Article>>
    suspend fun deleteArtice(article: Article)
}