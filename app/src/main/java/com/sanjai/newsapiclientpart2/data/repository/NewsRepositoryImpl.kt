package com.sanjai.newsapiclientpart2.data.repository

import com.sanjai.newsapiclientpart2.data.model.APIResponse
import com.sanjai.newsapiclientpart2.data.model.Article
import com.sanjai.newsapiclientpart2.data.repository.datasource.NewsLocalDataSource
import com.sanjai.newsapiclientpart2.data.repository.datasource.NewsRemoteDataSource
import com.sanjai.newsapiclientpart2.data.util.Resource
import com.sanjai.newsapiclientpart2.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class NewsRepositoryImpl(
    private val newsRemoteDataSource: NewsRemoteDataSource,
    private val newsLocalDataSource: NewsLocalDataSource
): NewsRepository {
    override suspend fun getNewsHeadlines(country: String, page: Int): Resource<APIResponse> {
        return responseToResource(newsRemoteDataSource.getNewsTopHeadlines(country, page))
    }

    override suspend fun getSearchedNews(
        country: String,
        page: Int,
        searchQuery: String
    ): Resource<APIResponse> {
        return responseToResource(newsRemoteDataSource.getSearchedNewsTopHeadlines(country, page, searchQuery))
    }

    private fun responseToResource(response: Response<APIResponse>): Resource<APIResponse> {
        if(response.isSuccessful) {
            response.body()?.let {result->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }

    override suspend fun saveNews(article: Article) {
        newsLocalDataSource.saveArticles(article)
    }

    override suspend fun deleteNews(article: Article) {
        newsLocalDataSource.deleteArtice(article)
    }

    override fun getSavedNews(): Flow<List<Article>> {
        return newsLocalDataSource.getSavedArticles()
    }

}