package com.ilesha.newstestapp.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.ilesha.newstestapp.data.local.dao.ArticleDao
import com.ilesha.newstestapp.data.mappers.toDomainModel
import com.ilesha.newstestapp.data.paging.NewsRemoteMediator
import com.ilesha.newstestapp.data.remote.api.ApiConstants
import com.ilesha.newstestapp.domain.model.Article
import com.ilesha.newstestapp.domain.model.NewsCategory
import com.ilesha.newstestapp.domain.repository.NewRepository
import dagger.assisted.AssistedFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class NewsRepository @Inject constructor(
    private val articleDao: ArticleDao,
    private val mediatorFactory: MediatorFactory
) : NewRepository {

    @AssistedFactory
    interface MediatorFactory {
        fun create(
            queryId: String,
            newsCategory: NewsCategory?
        ): NewsRemoteMediator
    }

    override fun getPagedNews(query: String): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(
                pageSize = ApiConstants.PAGE_SIZE
            ),
            remoteMediator = mediatorFactory.create(
                queryId = query,
                newsCategory = null
            ),
            pagingSourceFactory = {
                articleDao.getPagingSourceByQuery(query)
            }
        )
            .flow
            .map { pagingData ->
                pagingData.map {
                    it.toDomainModel()
                }
            }
    }

    override fun getPagedNewsByCategory(category: NewsCategory): Flow<PagingData<Article>> {
        val categoryId = category.categoryName
        return Pager(
            config = PagingConfig(
                pageSize = ApiConstants.PAGE_SIZE
            ),
            remoteMediator = mediatorFactory.create(
                queryId = categoryId,
                newsCategory = category
            ),
            pagingSourceFactory = {
                articleDao.getPagingSourceByQuery(categoryId)
            }
        )
            .flow
            .map { pagingData ->
                pagingData.map {
                    it.toDomainModel()
                }
            }
    }

}