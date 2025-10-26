@file:OptIn(ExperimentalMaterial3Api::class)

package com.ilesha.newstestapp.ui.screen.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.SearchBarState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSearchBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.ilesha.newstestapp.R
import com.ilesha.newstestapp.domain.model.Article
import com.ilesha.newstestapp.ui.common.NewsContent

@Composable
fun SearchScreen() {
    val viewModel: SearchViewModel = hiltViewModel()
    val articles = viewModel.articles.collectAsLazyPagingItems()
    val searchText by viewModel.searchText.collectAsStateWithLifecycle()
    val searchBarState = rememberSearchBarState()

    SearchScreenContent(
        articles = articles,
        searchText = searchText,
        searchBarState = searchBarState,
        onSearchTextChanged = viewModel::onSearchTextChange,
        onSearch = {
            viewModel.search()
        }
    )


}

@Composable
fun SearchScreenContent(
    articles: LazyPagingItems<Article>,
    searchText: String,
    searchBarState: SearchBarState,
    onSearchTextChanged: (String) -> Unit,
    onSearch: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        SearchPanel(
            searchBarState = searchBarState,
            searchText = searchText,
            onSearchTextChanged = {
                onSearchTextChanged(it)
            },
            onSearch = onSearch,
            modifier = Modifier
                .fillMaxWidth()
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
fun SearchPanel(
    searchBarState: SearchBarState,
    searchText: String,
    onSearchTextChanged: (String) -> Unit,
    onSearch: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current
    var isFocused by remember { mutableStateOf(false) }
    Surface(
        modifier = Modifier
            .padding(vertical = 5.dp)
    ) {
        SearchBar(
            state = searchBarState,
            inputField = {
                SearchBarDefaults.InputField(
                    query = searchText,
                    onQueryChange = onSearchTextChanged,
                    onSearch = {
                        onSearch(it)
                        focusManager.clearFocus()
                    },
                    expanded = true,
                    onExpandedChange = {},
                    placeholder = {
                        Text(
                            text = stringResource(R.string.search_screen_search_bar_placeholder)
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.Search,
                            contentDescription = stringResource(R.string.search_screen_search_bar_icon)
                        )
                    },
                    trailingIcon = {
                        if (isFocused || searchText.isNotEmpty()) {
                            IconButton(
                                onClick = {
                                    onSearchTextChanged("")
                                    focusManager.clearFocus()
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.Clear,
                                    contentDescription = Icons.Outlined.Clear.name
                                )
                            }
                        }
                    },
                    modifier = modifier
                        .onFocusChanged { focusState ->
                            isFocused = focusState.isFocused
                        }
                )
            }
        )
    }
}