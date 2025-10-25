package com.ilesha.newstestapp.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.ilesha.newstestapp.data.local.db.NewsDatabase
import com.ilesha.newstestapp.data.local.entity.ArticleEntity
import com.ilesha.newstestapp.data.local.entity.RemoteKeysEntity
import com.ilesha.newstestapp.data.mappers.toArticleEntity
import com.ilesha.newstestapp.data.mappers.toDomainModel
import com.ilesha.newstestapp.data.remote.api.ApiConstants
import com.ilesha.newstestapp.data.remote.api.NewsApiService
import com.ilesha.newstestapp.data.remote.dto.NewsResponseDto
import com.ilesha.newstestapp.domain.model.NewsCategory
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class NewsRemoteMediator @Inject constructor(
    private val queryId: String,
    private val newsCategory: NewsCategory?,
    private val database: NewsDatabase,
    private val newsApiService: NewsApiService
) : RemoteMediator<Int, ArticleEntity>() {

    companion object {
        private const val START_PAGE_INDEX = 1
    }

    private val articleDao = database.articleDao()
    private val remoteKeysDao = database.remoteKeysDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ArticleEntity>
    ): MediatorResult {
        val pageKey: Int? = when (loadType) {
            LoadType.REFRESH -> {
                START_PAGE_INDEX
            }

            LoadType.APPEND -> {
                val remoteKeys = remoteKeysDao.getRemoteKeysByQuery(queryId)
                remoteKeys?.nextKey
            }

            LoadType.PREPEND -> {
                null
            }
        }
        val page = pageKey ?: return MediatorResult.Success(endOfPaginationReached = true)

        return try {
            processAndMapResponse(
                page = page,
                state = state,
                loadType = loadType
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun getResponse(
        page: Int,
        state: PagingState<Int, ArticleEntity>
    ): NewsResponseDto {
        return if (newsCategory != null) {
            newsApiService.getNewsByCategory(
                category = newsCategory.categoryName,
                page = page,
                pageSize = state.config.pageSize
            )
        } else {
            newsApiService.searchNews(
                query = queryId,
                page = page,
                pageSize = state.config.pageSize
            )
        }
    }

    private suspend fun processAndMapResponse(
        page: Int,
        state: PagingState<Int, ArticleEntity>,
        loadType: LoadType
    ): MediatorResult.Success {
        val response = getResponse(page, state)
        if (response.status != ApiConstants.STATUS_OK) throw IOException(response.status)
        val articles = response.articles ?: emptyList()
        val totalResults = response.totalResults ?: 0
        val articleEntities = articles
            .mapNotNull { it.toDomainModel() }
            .map { it.toArticleEntity(queryId) }
        val totalResultsReached = totalResults <= state.config.pageSize * page
        val isPaginationEndReached =
            totalResultsReached || (articles.isNotEmpty() && articleEntities.isEmpty())
        val nextKey = if (isPaginationEndReached) null else page.plus(1)
        val prevKey = if (page == START_PAGE_INDEX) null else page.minus(1)

        database.withTransaction {
            if (loadType == LoadType.REFRESH) {
                articleDao.deleteArticlesByQuery(queryId)
                remoteKeysDao.deleteRemoteKeysByQuery(queryId)
            }
            articleDao.insertAllArticles(articleEntities)
            remoteKeysDao.insertRemoteKey(
                RemoteKeysEntity(
                    queryId = queryId,
                    prevKey = prevKey,
                    nextKey = nextKey
                )
            )
        }

        return MediatorResult.Success(endOfPaginationReached = isPaginationEndReached)
    }

}