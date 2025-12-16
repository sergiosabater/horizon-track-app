package com.example.horizontrack.ui.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

/**
 * Navigation routes for the app.
 */
sealed class Screen(val route: String, val navArguments: List<NamedNavArgument> = emptyList()) {
    object HabitsList : Screen("habits_list")

    object HabitCreate : Screen("habit_create")

    object HabitEdit : Screen(
        route = "habit_edit/{habitId}",
        navArguments = listOf(
            navArgument("habitId") { type = NavType.LongType },
        ),
    ) {
        fun createRoute(habitId: Long) = "habit_edit/$habitId"
    }

    object HabitDetail : Screen(
        route = "habit_detail/{habitId}",
        navArguments = listOf(
            navArgument("habitId") { type = NavType.LongType },
        ),
    ) {
        fun createRoute(habitId: Long) = "habit_detail/$habitId"
    }

    object Statistics : Screen("statistics")
}
