package ru.eamshokov.stepstracker.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ru.eamshokov.stepstracker.R
import ru.eamshokov.stepstracker.ui.screen.activity.ActivityView
import ru.eamshokov.stepstracker.ui.screen.login.LoginView
import ru.eamshokov.stepstracker.ui.screen.register.RegisterView
import ru.eamshokov.stepstracker.ui.screen.statistics.StatisticsView
import ru.eamshokov.stepstracker.ui.theme.StepsTrackerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StepsTrackerTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    MainView()
                }
            }
        }
    }
}

@Composable
fun MainView(viewModel:MainViewModel = viewModel()) {
    val navController = rememberNavController()
    val mainScreenPages = listOf(
        MainScreenPage.Activity,
        MainScreenPage.Statistics
    )
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        content = {
            NavHost(navController = navController, startDestination = "login") {
                composable("login") { LoginView(navController = navController) }
                composable("register") { RegisterView(navController = navController) }
                composable("activity") { ActivityView(navController = navController) }
                composable("statistics") { StatisticsView(navController = navController) }
            }
        },
        bottomBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            if ( currentDestination?.hierarchy?.any { it.route in mainScreenPages.map {it.route} } == true) {
                BottomNav(pages = mainScreenPages, navController = navController)
            }
        }
    )
}

@Composable
fun BottomNav(pages: List<MainScreenPage>, navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    BottomAppBar(
        elevation = 0.dp,
        content = {
            BottomNavigation {
                pages.forEach { entry ->
                    val selected =
                        currentDestination?.hierarchy?.any { it.route == entry.route } == true
                    BottomNavigationItem(
                        selected = selected,
                        onClick = {
                            navController.navigate(entry.route) {
                                // Pop up to the start destination of the graph to
                                // avoid building up a large stack of destinations
                                // on the back stack as users select items
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                // Avoid multiple copies of the same destination when
                                // reselecting the same item
                                launchSingleTop = true
                                // Restore state when reselecting a previously selected item
                                restoreState = true

                            }
                        },
                        icon = {
                            Icon(
                                painterResource(id = entry.icon),
                                "",
                                tint = if (selected)
                                    MaterialTheme.colors.onBackground
                                else MaterialTheme.colors.onPrimary
                            )
                        },
                        label = { Text(entry.label) }
                    )
                }

            }
        }
    )
}

sealed class MainScreenPage(
    val route: String,
    @DrawableRes
    val icon: Int,
    val label: String
) {
    object Activity : MainScreenPage("activity", R.drawable.ic_activity, "Activity")
    object Statistics : MainScreenPage("statistics", R.drawable.ic_statistics, "Statistics")
}