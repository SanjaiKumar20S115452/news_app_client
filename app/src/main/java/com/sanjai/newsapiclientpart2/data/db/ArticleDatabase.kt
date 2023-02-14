package com.sanjai.newsapiclientpart2.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sanjai.newsapiclientpart2.data.model.Article

@Database(entities = [Article::class],version = 1,exportSchema = false)
@TypeConverters(Converters::class)
abstract class ArticleDatabase: RoomDatabase() {

    abstract fun getArticleDAO(): ArticleDAO

}