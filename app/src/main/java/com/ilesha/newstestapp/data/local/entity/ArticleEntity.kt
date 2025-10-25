package com.ilesha.newstestapp.data.local.entity

import androidx.room.Entity
import java.time.Instant

@Entity(tableName = "article", primaryKeys = ["url", "searchQuery"])
data class ArticleEntity(
    val url: String,
    val searchQuery: String,
    val author: String,
    val title: String,
    val description: String,
    val urlToImage: String,
    val publishedAt: Instant?,
    val content: String,
    val sourceId: String?,
    val sourceName: String
)