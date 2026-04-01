package com.example.expensetracker
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.example.expensetracker.common.preferences.UserPreferencesRepository
import com.example.expensetracker.navigation.AppNavGraph
import com.example.expensetracker.navigation.Screen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var userPreferencesRepository: UserPreferencesRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            ExpenseTrackerAppContent(userPreferencesRepository = userPreferencesRepository)
        }
    }
}
@Composable
private fun ExpenseTrackerAppContent(
    userPreferencesRepository: UserPreferencesRepository
) {
    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val navController = rememberNavController()

            val isOnboardingCompleted by userPreferencesRepository.isOnboardingCompleted
                .collectAsState(initial = false)

            val startDestination = if (isOnboardingCompleted) {
                Screen.Dashboard.route

            } else {
                Screen.Onboarding.route
            }

            AppNavGraph(
                navController = navController,
                startDestination = startDestination
            )
        }
    }
}