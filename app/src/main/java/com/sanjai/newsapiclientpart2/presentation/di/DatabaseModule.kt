package com.sanjai.newsapiclientpart2.presentation.di

import android.app.Application
import androidx.room.Room
import com.sanjai.newsapiclientpart2.data.db.ArticleDAO
import com.sanjai.newsapiclientpart2.data.db.ArticleDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideArticleDatabaseInstanceModule(
        app: Application
    ): ArticleDatabase {
        return Room.databaseBuilder(
            app.applicationContext,
            ArticleDatabase::class.java,
            "article_db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideArticleDAOInstanceModule(articleDatabase: ArticleDatabase): ArticleDAO {
        return articleDatabase.getArticleDAO()
    }

}