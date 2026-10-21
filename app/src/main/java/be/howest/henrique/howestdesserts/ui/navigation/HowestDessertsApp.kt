package be.howest.henrique.howestdesserts.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import be.howest.henrique.howestdesserts.ui.DessertDetailRoute
import be.howest.henrique.howestdesserts.ui.DessertRoute
import be.howest.henrique.howestdesserts.ui.cart.CartRoute


@Composable
fun HowestDessertsApp(
    navController: NavHostController = rememberNavController()
) {

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination
    val canNavigateBack = currentDestination?.route == Routes.DESSERT_DETAIL

    Scaffold(
        topBar = {
            DessertsTopAppBar(
                canNavigateBack = canNavigateBack,
                onNavigateUp = { navController.navigateUp() }
            )
        },
        bottomBar = {
            DessertsBottomBar(
                currentDestination = currentDestination,
                onTabSelected = { route ->
                    navController.navigate(route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Routes.DESSERTS_GRAPH,
            modifier = Modifier.padding(innerPadding)
        ) {
            // ----- Desserts tab: list + detail -----
            navigation(
                startDestination = Routes.DESSERT_LIST,
                route = Routes.DESSERTS_GRAPH
            ) {
                composable(Routes.DESSERT_LIST) {
                    DessertRoute(
                        onDessertClick = { dessertId ->
                            navController.navigate(Routes.dessertDetail(dessertId))
                        }
                    )
                }
                composable(
                    route = Routes.DESSERT_DETAIL,
                    arguments = listOf(
                        navArgument(Routes.DESSERT_ID_ARG) { type = NavType.IntType }
                    )
                ) {
                    // The ID reaches DessertDetailViewModel through its SavedStateHandle.
                    DessertDetailRoute()
                }
            }
            composable(Routes.CART) {
                CartRoute()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DessertsTopAppBar(
    canNavigateBack: Boolean,
    onNavigateUp: () -> Unit
) {
    CenterAlignedTopAppBar(
        title = { Text("Howest Desserts") },
        navigationIcon = {
            // Up button: only on the detail screen
            if (canNavigateBack) {
                IconButton(onClick = onNavigateUp) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
            }
        }
    )
}

@Composable
private fun DessertsBottomBar(
    currentDestination: NavDestination?,
    onTabSelected: (String) -> Unit
) {
    NavigationBar {
        TopLevelDestination.entries.forEach { destination ->
            val selected = currentDestination?.hierarchy?.any { it.route == destination.route } == true
            NavigationBarItem(
                selected = selected,
                onClick = { onTabSelected(destination.route) },
                icon = { Icon(destination.icon, contentDescription = null) },
                label = { Text(destination.label) }
            )
        }
    }
}