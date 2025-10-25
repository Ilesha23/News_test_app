package com.ilesha.newstestapp.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.ilesha.newstestapp.R

data class BottomNavBarItem(
    val route: String,
    val icon: ImageVector,
    val label: String
)

@Composable
fun BottomNavBar(
    navController: NavController
) {
    val items = listOf(
        BottomNavBarItem(
            Route.Search.route,
            Icons.Outlined.Search,
            stringResource(R.string.bottom_nav_bar_label_search)
        ),
        BottomNavBarItem(
            Route.Categories.route,
            Icons.AutoMirrored.Outlined.List,
            stringResource(R.string.bottom_nav_bar_label_categories)
        ),
    )

    val currentNavBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentNavBackStackEntry?.destination?.route

    NavigationBar {
        items.forEach { navBarItem ->
            val isSelected = currentRoute == navBarItem.route
            NavigationBarItem(
                selected = isSelected,
                icon = {
                    Icon(
                        imageVector = navBarItem.icon,
                        contentDescription = navBarItem.label
                    )
                },
                label = {
                    Text(
                        text = navBarItem.label
                    )
                },
                onClick = {
                    if (currentRoute != navBarItem.route) {
                        navController.navigate(navBarItem.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }
    }
}

