package com.ilesha.newstestapp.domain.model

import java.time.Instant

data class Article(
    val source: Source,
    val author: String,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: Instant?,
    val content: String,
)
