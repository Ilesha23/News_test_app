package com.ilesha.newstestapp.ui.screen.categories

import androidx.lifecycle.ViewModel
import com.ilesha.newstestapp.domain.repository.NewRepository
import javax.inject.Inject

class CategoriesViewModel @Inject constructor(
    private val newsRepository: NewRepository
) : ViewModel() {

}