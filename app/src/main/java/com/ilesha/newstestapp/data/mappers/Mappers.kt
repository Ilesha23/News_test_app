package com.ilesha.newstestapp.data.mappers

import com.ilesha.newstestapp.data.local.entity.ArticleEntity
import com.ilesha.newstestapp.data.remote.dto.ArticleDto
import com.ilesha.newstestapp.domain.model.Article
import com.ilesha.newstestapp.domain.model.Source
import java.time.Instant
import java.time.format.DateTimeParseException

fun ArticleDto.toDomainModel(): Article? {
    val url = this.url ?: return null
    val publishedAt = try {
        this.publishedAt?.let {
            Instant.parse(it)
        }
    } catch (e: DateTimeParseException) {
        null
    }

    return Article(
        source = Source(
            id = source?.id ?: "",
            name = source?.name ?: ""
        ),
        author = author ?: "",
        title = title ?: "",
        description = description ?: "",
        url = url,
        urlToImage = urlToImage ?: "",
        publishedAt = publishedAt,
        content = content ?: "",
    )
}

fun Article.toArticleEntity(searchQuery: String): ArticleEntity {
    return ArticleEntity(
        url = url,
        searchQuery = searchQuery,
        author = author,
        title = title,
        description = description,
        urlToImage = urlToImage,
        publishedAt = publishedAt,
        content = content,
        sourceName = source.name,
        sourceId = source.id
    )
}

fun ArticleEntity.toDomainModel(): Article {
    return Article(
        source = Source(
            id = sourceId,
            name = sourceName
        ),
        author = author,
        title = title,
        description = description,
        url = url,
        urlToImage = urlToImage,
        publishedAt = publishedAt,
        content = content
    )
}