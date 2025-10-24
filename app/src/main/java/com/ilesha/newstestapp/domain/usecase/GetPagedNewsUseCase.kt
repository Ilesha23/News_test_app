package com.ilesha.newstestapp.domain.usecase

import androidx.paging.PagingData
import com.ilesha.newstestapp.domain.model.Article
import com.ilesha.newstestapp.domain.repository.NewRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class GetPagedNewsUseCase @Inject constructor(
    private val newsRepository: NewRepository
) {

    operator fun invoke(query: String): Flow<PagingData<Article>> {
        if (query.isBlank())
            return flowOf(PagingData.empty())
        return newsRepository.getPagedNews(query)
    }

}