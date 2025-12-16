package com.example.horizontrack.ui

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.room.Room
import com.example.horizontrack.data.AchievementRepositoryImpl
import com.example.horizontrack.data.HabitRepositoryImpl
import com.example.horizontrack.data.UserProgressRepositoryImpl
import com.example.horizontrack.data.local.HorizonTrackDatabase
import com.example.horizontrack.domain.repository.AchievementRepository
import com.example.horizontrack.domain.repository.HabitRepository
import com.example.horizontrack.domain.repository.UserProgressRepository

/**
 * Container for app-level dependencies (repositories, database, etc.)
 * This follows clean architecture principles and makes testing easier.
 */
data class AppDependencies(
    val habitRepository: HabitRepository,
    val userProgressRepository: UserProgressRepository,
    val achievementRepository: AchievementRepository,
)

/**
 * Creates and remembers app dependencies. Room database is initialized here.
 * In a production app, you might want to use a DI framework like Hilt or Koin.
 */
@Composable
fun rememberAppDependencies(context: Context): AppDependencies {
    return remember {
        val database = Room.databaseBuilder(
            context,
            HorizonTrackDatabase::class.java,
            "horizon_track_database"
        ).build()

        val habitDao = database.habitDao()
        val habitRepository = HabitRepositoryImpl(habitDao)
        val userProgressRepository = UserProgressRepositoryImpl(context)
        val achievementRepository = AchievementRepositoryImpl(context)

        AppDependencies(
            habitRepository = habitRepository,
            userProgressRepository = userProgressRepository,
            achievementRepository = achievementRepository,
        )
    }
}

