package com.ilesha.newstestapp.ui.screen.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.ilesha.newstestapp.domain.usecase.GetPagedNewsByCategoryUseCase
import com.ilesha.newstestapp.ui.common.NewsCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val getPagedNewsByCategoryUseCase: GetPagedNewsByCategoryUseCase
) : ViewModel() {

    private val _selectedCategory = MutableStateFlow(NewsCategory.GENERAL)
    val selectedCategory = _selectedCategory.asStateFlow()

    val articles = _selectedCategory
        .flatMapLatest {
            getPagedNewsByCategoryUseCase(it.name)
        }
        .cachedIn(viewModelScope)

    fun onCategoryChanged(category: NewsCategory) {
        _selectedCategory.update { category }
    }

}