package com.ilesha.newstestapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class NewsResponseDto(
    @SerializedName("status")
    val status: String?,
    @SerializedName("totalResults")
    val totalResults: Long?,
    @SerializedName("articles")
    val articles: List<ArticleDto>?,
)
