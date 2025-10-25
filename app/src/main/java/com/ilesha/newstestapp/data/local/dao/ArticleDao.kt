package com.ilesha.newstestapp.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ilesha.newstestapp.data.local.entity.ArticleEntity

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllArticles(articles: List<ArticleEntity>)

    @Query("SELECT * FROM article WHERE searchQuery = :queryId ORDER BY publishedAt DESC")
    fun getPagingSourceByQuery(queryId: String): PagingSource<Int, ArticleEntity>

    @Query("DELETE FROM article WHERE searchQuery = :queryId")
    suspend fun deleteArticlesByQuery(queryId: String)

}