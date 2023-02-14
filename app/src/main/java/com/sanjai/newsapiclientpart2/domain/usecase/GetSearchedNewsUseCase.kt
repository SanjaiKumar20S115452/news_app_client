package com.sanjai.newsapiclientpart2.domain.usecase

import com.sanjai.newsapiclientpart2.domain.repository.NewsRepository

class GetSearchedNewsUseCase(private val newsRepository: NewsRepository) {
    suspend fun execute(country: String,page: Int,searchQuery: String) = newsRepository.getSearchedNews(country, page, searchQuery)
}