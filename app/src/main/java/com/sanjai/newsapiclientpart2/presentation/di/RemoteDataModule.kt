package com.sanjai.newsapiclientpart2.presentation.di

import com.sanjai.newsapiclientpart2.data.api.NewsAPIService
import com.sanjai.newsapiclientpart2.data.repository.datasource.NewsRemoteDataSource
import com.sanjai.newsapiclientpart2.data.repository.datasourceImpl.NewsRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RemoteDataModule {

    @Singleton
    @Provides
    fun provideNewsRemoteDataSourceInstanceModule(newsAPIService: NewsAPIService): NewsRemoteDataSource {
        return NewsRemoteDataSourceImpl(newsAPIService)
    }

}