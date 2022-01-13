package de.hshl.isd.mensa

import android.util.Log
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import de.hshl.isd.mensa.ui.theme.MensaTheme
import io.github.italbytz.ports.meal.MealCollection
import java.time.LocalDate

@Composable
fun MainContent() {
    val navController = rememberNavController()
    MensaTheme {
        Scaffold(
            topBar = {
                TopAppBar(title = {
                    Text(text = "Mensa")
                })
            },
            bottomBar = { BottomBar(navController) }
        ) {
            NavigationHost(navController)
        }
    }
}

@Composable
fun BottomBar(navController: NavHostController) {
    val items = listOf(
        Screen.Mensa,
        Screen.Settings,
    )

    BottomNavigation {
        val navBackStackEntry by
        navController.currentBackStackEntryAsState()
        val currentDestination =
            navBackStackEntry?.destination
        items.forEach { screen ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        imageVector = screen.icon!!,
                        contentDescription = stringResource(screen.resourceId!!)
                    )
                },
                label = { Text(stringResource(screen.resourceId!!)) },
                selected = currentDestination?.hierarchy?.any {
                    it.route == screen.route
                } == true,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}