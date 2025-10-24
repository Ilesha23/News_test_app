package com.ilesha.newstestapp.domain.usecase

import androidx.paging.PagingData
import com.ilesha.newstestapp.domain.model.Article
import com.ilesha.newstestapp.domain.model.NewsCategory
import com.ilesha.newstestapp.domain.repository.NewRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPagedNewsByCategory @Inject constructor(
    private val newRepository: NewRepository
) {

    operator fun invoke(category: NewsCategory): Flow<PagingData<Article>> {
        return newRepository.getPagedNewsByCategory(category)
    }

}