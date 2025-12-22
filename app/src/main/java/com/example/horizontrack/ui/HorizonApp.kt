package com.example.horizontrack.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.horizontrack.ui.habits.HabitCreateScreen
import com.example.horizontrack.ui.habits.HabitDetailScreen
import com.example.horizontrack.ui.habits.HabitEditScreen
import com.example.horizontrack.ui.habits.HabitsListScreen
import com.example.horizontrack.ui.navigation.Screen
import com.example.horizontrack.ui.statistics.StatisticsScreen

/**
 * Main app composable that sets up navigation and theme.
 */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HorizonApp(
    dependencies: AppDependencies,
    modifier: Modifier = Modifier,
) {
    val navController = rememberNavController()

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        NavHost(
            navController = navController,
            startDestination = Screen.HabitsList.route,
        ) {
            composable(Screen.HabitsList.route) {
                HabitsListScreen(
                    habitRepository = dependencies.habitRepository,
                    onNavigateToHabitDetail = { habitId ->
                        navController.navigate(Screen.HabitDetail.createRoute(habitId))
                    },
                    userProgressRepository = dependencies.userProgressRepository,
                    achievementRepository = dependencies.achievementRepository,
                    onAddHabit = {
                        navController.navigate(Screen.HabitCreate.route)
                    },
                    onNavigateToStatistics = {
                        navController.navigate(Screen.Statistics.route)
                    },
                )
            }
            composable(
                route = Screen.HabitCreate.route,
            ) {
                HabitCreateScreen(
                    habitRepository = dependencies.habitRepository,
                    onNavigateBack = { navController.popBackStack() },
                )
            }
            composable(
                route = Screen.HabitDetail.route,
                arguments = Screen.HabitDetail.navArguments,
            ) { backStackEntry ->
                val habitId = backStackEntry.arguments?.getLong("habitId") ?: 0L
                HabitDetailScreen(
                    habitId = habitId,
                    habitRepository = dependencies.habitRepository,
                    userProgressRepository = dependencies.userProgressRepository,
                    achievementRepository = dependencies.achievementRepository,
                    onNavigateBack = { navController.popBackStack() },
                    onNavigateToEdit = {
                        navController.navigate(Screen.HabitEdit.createRoute(habitId))
                    },
                )
            }
            composable(
                route = Screen.HabitEdit.route,
                arguments = Screen.HabitEdit.navArguments,
            ) { backStackEntry ->
                val habitId = backStackEntry.arguments?.getLong("habitId") ?: 0L
                HabitEditScreen(
                    habitId = habitId,
                    habitRepository = dependencies.habitRepository,
                    onNavigateBack = { navController.popBackStack() },
                )
            }
            composable(Screen.Statistics.route) {
                StatisticsScreen(
                    habitRepository = dependencies.habitRepository,
                    userProgressRepository = dependencies.userProgressRepository,
                    achievementRepository = dependencies.achievementRepository,
                    onNavigateBack = { navController.popBackStack() },
                )
            }
        }
    }
}

