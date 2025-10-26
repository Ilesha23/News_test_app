package com.ilesha.newstestapp.ui.screen.categories

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.ilesha.newstestapp.domain.model.Article
import com.ilesha.newstestapp.ui.common.NewsCategory
import com.ilesha.newstestapp.ui.common.NewsContent

@Composable
fun CategoriesScreen() {

    val viewModel: CategoriesViewModel = hiltViewModel()
    val selectedCategory by viewModel.selectedCategory.collectAsStateWithLifecycle()
    val articles = viewModel.articles.collectAsLazyPagingItems()

    CategoryScreenContent(
        articles = articles,
        selectedCategory = selectedCategory,
        allCategories = NewsCategory.entries,
        onCategorySelected = viewModel::onCategoryChanged
    )
}

@Composable
fun CategoryScreenContent(
    articles: LazyPagingItems<Article>,
    selectedCategory: NewsCategory,
    allCategories: List<NewsCategory>,
    onCategorySelected: (NewsCategory) -> Unit
) {

    Column {
        CategorySelectionRow(
            selectedCategory = selectedCategory,
            allCategories = allCategories,
            onCategorySelected = onCategorySelected
        )
        NewsContent(
            articles = articles,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
        )
    }

}

@Composable
fun CategorySelectionRow(
    selectedCategory: NewsCategory,
    allCategories: List<NewsCategory>,
    onCategorySelected: (NewsCategory) -> Unit
) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        allCategories.forEach { category ->
            FilterChip(
                selected = selectedCategory == category,
                onClick = {
                    onCategorySelected(category)
                },
                label = {
                    Text(
                        text = stringResource(category.stringResId)
                    )
                }
            )
        }
    }
}