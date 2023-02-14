package com.sanjai.newsapiclientpart2.data.db

import androidx.room.*
import com.sanjai.newsapiclientpart2.data.model.Article
import kotlinx.coroutines.flow.Flow
import retrofit2.http.DELETE

@Dao
interface ArticleDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(article: Article)

    @Query("SELECT * FROM articles")
    fun getSavedArticle(): Flow<List<Article>>

    @Delete
    suspend fun deleteArticle(article: Article)
}