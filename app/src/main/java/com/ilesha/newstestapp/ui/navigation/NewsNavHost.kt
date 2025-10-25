package com.ilesha.newstestapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ilesha.newstestapp.ui.screen.categories.CategoriesScreen
import com.ilesha.newstestapp.ui.screen.search.SearchScreen

@Composable
fun NewsNavHost(
    navController: NavHostController,
    modifier: Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Route.Search.route,
        modifier = modifier
    ) {

        composable(Route.Search.route) {
            SearchScreen()
        }

        composable(Route.Categories.route) {
            CategoriesScreen()
        }

    }
}