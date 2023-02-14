package com.sanjai.newsapiclientpart2.presentation.di

import com.sanjai.newsapiclientpart2.data.repository.NewsRepositoryImpl
import com.sanjai.newsapiclientpart2.data.repository.datasource.NewsLocalDataSource
import com.sanjai.newsapiclientpart2.data.repository.datasource.NewsRemoteDataSource
import com.sanjai.newsapiclientpart2.domain.repository.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideNewsRepositoryInstanceModule(
        newsRemoteDataSource: NewsRemoteDataSource,
        newsLocalDataSource: NewsLocalDataSource
    ): NewsRepository {
        return NewsRepositoryImpl(newsRemoteDataSource,newsLocalDataSource)
    }
}