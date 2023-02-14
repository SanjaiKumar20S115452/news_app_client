package com.sanjai.newsapiclientpart2.presentation.di

import com.sanjai.newsapiclientpart2.presentation.adapter.NewsAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AdapterModule {

    @Singleton
    @Provides
    fun provideNewsAdapterInstanceModule(): NewsAdapter {
        return NewsAdapter()
    }

}