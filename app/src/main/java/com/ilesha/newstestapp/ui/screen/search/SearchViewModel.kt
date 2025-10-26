package com.ilesha.newstestapp.ui.screen.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ilesha.newstestapp.domain.usecase.GetPagedNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getPagedNewsUseCase: GetPagedNewsUseCase
) : ViewModel() {

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val query = MutableStateFlow("")

    val articles = query
        .flatMapLatest {
            if (it.isEmpty()) {
                flowOf(PagingData.empty())
            } else {
                getPagedNewsUseCase(it)
            }
        }
        .cachedIn(viewModelScope)

    fun onSearchTextChange(text: String) {
        _searchText.update { text }
    }

    fun search() {
        query.update { _searchText.value.trim() }
    }

}