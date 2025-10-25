package com.ilesha.newstestapp.ui.navigation

sealed class Route(val route: String) {
    data object Search : Route("home_screen")
    data object Categories : Route("categories_screen")
}