package com.sanjai.newsapiclientpart2.presentation.di

import com.sanjai.newsapiclientpart2.data.db.ArticleDAO
import com.sanjai.newsapiclientpart2.data.repository.datasource.NewsLocalDataSource
import com.sanjai.newsapiclientpart2.data.repository.datasourceImpl.NewsLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalDataModule {

    @Singleton
    @Provides
    fun provideArticleNewsLocalDataSourceInstanceModule(articleDAO: ArticleDAO): NewsLocalDataSource {
        return NewsLocalDataSourceImpl(articleDAO)
    }

}