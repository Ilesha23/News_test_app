package com.ilesha.newstestapp.ui.screen.search

import androidx.lifecycle.ViewModel
import com.ilesha.newstestapp.domain.repository.NewRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val newsRepository: NewRepository
) : ViewModel() {



}