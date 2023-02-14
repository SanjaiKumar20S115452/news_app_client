package com.sanjai.newsapiclientpart2.domain.usecase

import com.sanjai.newsapiclientpart2.data.model.APIResponse
import com.sanjai.newsapiclientpart2.data.util.Resource
import com.sanjai.newsapiclientpart2.domain.repository.NewsRepository

class GetNewsHeadlinesUseCase(private val newsRepository: NewsRepository) {
    suspend fun execute(country: String,page: Int): Resource<APIResponse> = newsRepository.getNewsHeadlines(country, page)
}