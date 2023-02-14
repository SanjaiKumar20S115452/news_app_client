package com.sanjai.newsapiclientpart2.domain.usecase

import com.sanjai.newsapiclientpart2.data.model.Article
import com.sanjai.newsapiclientpart2.domain.repository.NewsRepository

class DeleteSavedNewsUseCase(private val newsRepository: NewsRepository) {
    suspend fun execute(article: Article) = newsRepository.deleteNews(article)
}