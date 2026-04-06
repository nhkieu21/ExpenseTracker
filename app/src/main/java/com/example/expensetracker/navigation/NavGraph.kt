package com.example.expensetracker.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.expensetracker.ui.dashboard.DashboardScreen
import com.example.expensetracker.ui.onboarding.OnboardingScreen

sealed class Screen(val route: String) {
    data object Onboarding : Screen("onboarding_screen")
    data object Dashboard : Screen("dashboard_screen")
}

@Composable
fun AppNavGraph(
    navController: NavHostController,
    startDestination: String = Screen.Onboarding.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(route = Screen.Onboarding.route) {
            OnboardingScreen(
                onNavigateToDashboard = {
                    navController.navigate(Screen.Dashboard.route) {
                        popUpTo(Screen.Onboarding.route) { inclusive = true }
                    }
                }
            )
        }

        composable(route = Screen.Dashboard.route) {
            DashboardScreen()
        }
    }
}

