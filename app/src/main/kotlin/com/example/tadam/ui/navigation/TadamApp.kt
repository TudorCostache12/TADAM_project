package com.example.tadam.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.tadam.ui.screens.ConverterScreen
import com.example.tadam.ui.screens.RatesScreen
import com.example.tadam.viewmodel.CurrencyViewModel

private sealed class BottomDestination(val route: String, val label: String, val icon: androidx.compose.ui.graphics.vector.ImageVector) {
    data object Rates : BottomDestination("rates", "Rates", Icons.Filled.BarChart)
    data object Converter : BottomDestination("converter", "Converter", Icons.Filled.SwapHoriz)
}

@Composable
fun TadamApp() {
    val navController = rememberNavController()
    val viewModel = remember { CurrencyViewModel() }
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route?.substringBefore("/")
    val destinations = listOf(BottomDestination.Rates, BottomDestination.Converter)

    Scaffold(
        bottomBar = {
            NavigationBar {
                destinations.forEach { destination ->
                    NavigationBarItem(
                        selected = currentRoute == destination.route,
                        onClick = {
                            if (currentRoute != destination.route) {
                                navController.navigate(destination.route) {
                                    launchSingleTop = true
                                    restoreState = true
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                }
                            }
                        },
                        icon = { Icon(destination.icon, contentDescription = destination.label) },
                        label = { Text(destination.label) },
                    )
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = BottomDestination.Rates.route,
            modifier = androidx.compose.ui.Modifier.padding(paddingValues),
        ) {
            composable(BottomDestination.Rates.route) {
                RatesScreen(
                    viewModel = viewModel,
                    onOpenConverter = {
                        navController.navigate(BottomDestination.Converter.route) {
                            launchSingleTop = true
                        }
                    },
                )
            }
            composable(BottomDestination.Converter.route) {
                ConverterScreen(viewModel = viewModel)
            }
        }
    }
}
