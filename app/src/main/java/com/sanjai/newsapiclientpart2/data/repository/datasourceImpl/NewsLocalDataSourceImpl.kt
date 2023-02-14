package com.sanjai.newsapiclientpart2.data.repository.datasourceImpl

import com.sanjai.newsapiclientpart2.data.db.ArticleDAO
import com.sanjai.newsapiclientpart2.data.model.Article
import com.sanjai.newsapiclientpart2.data.repository.datasource.NewsLocalDataSource
import kotlinx.coroutines.flow.Flow

class NewsLocalDataSourceImpl(
    private val articleDAO: ArticleDAO
): NewsLocalDataSource {
    override suspend fun saveArticles(article: Article) {
        return articleDAO.insert(article)
    }

    override fun getSavedArticles(): Flow<List<Article>> {
        return articleDAO.getSavedArticle()
    }

    override suspend fun deleteArtice(article: Article) {
        articleDAO.deleteArticle(article)
    }
}