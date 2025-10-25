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
            id = this.source?.id ?: "",
            name = this.source?.name ?: ""
        ),
        author = this.author ?: "",
        title = this.title ?: "",
        description = this.description ?: "",
        url = url,
        urlToImage = this.urlToImage ?: "",
        publishedAt = publishedAt,
        content = this.content ?: "",
    )
}

fun Article.toArticleEntity(searchQuery: String): ArticleEntity {
    return ArticleEntity(
        url = this.url,
        searchQuery = searchQuery,
        author = this.author,
        title = this.title,
        description = this.description,
        urlToImage = this.urlToImage,
        publishedAt = this.publishedAt,
        content = this.content,
        sourceName = this.source.name,
        sourceId = this.source.id
    )
}

fun ArticleEntity.toDomainModel(): Article {
    return Article(
        source = Source(
            id = this.sourceId,
            name = this.sourceName
        ),
        author = this.author,
        title = this.title,
        description = this.description,
        url = this.url,
        urlToImage = this.urlToImage,
        publishedAt = this.publishedAt,
        content = this.content
    )
}