package com.ilesha.newstestapp.domain.repository

import androidx.paging.PagingData
import com.ilesha.newstestapp.domain.model.Article
import com.ilesha.newstestapp.domain.model.NewsCategory
import kotlinx.coroutines.flow.Flow

interface NewRepository {

    fun getPagedNews(query: String): Flow<PagingData<Article>>

    fun getPagedNewsByCategory(category: NewsCategory): Flow<PagingData<Article>>

}