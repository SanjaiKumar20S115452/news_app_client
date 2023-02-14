package com.sanjai.newsapiclientpart2.domain.usecase

import com.sanjai.newsapiclientpart2.data.model.Article
import com.sanjai.newsapiclientpart2.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class GetSavedNewsUseCase(private val newsRepository: NewsRepository) {
    suspend fun execute(): Flow<List<Article>> = newsRepository.getSavedNews()
}