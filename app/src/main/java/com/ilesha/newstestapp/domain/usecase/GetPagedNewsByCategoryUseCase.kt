package com.ilesha.newstestapp.domain.usecase

import androidx.paging.PagingData
import com.ilesha.newstestapp.domain.model.Article
import com.ilesha.newstestapp.domain.repository.NewRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPagedNewsByCategoryUseCase @Inject constructor(
    private val newRepository: NewRepository
) {

    operator fun invoke(category: String): Flow<PagingData<Article>> {
        return newRepository.getPagedNewsByCategory(category)
    }

}