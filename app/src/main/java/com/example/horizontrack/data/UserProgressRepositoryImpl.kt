package com.example.horizontrack.data

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.horizontrack.domain.model.UserProgress
import com.example.horizontrack.domain.repository.UserProgressRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate

private const val DATA_STORE_NAME = "user_progress"

val Context.userProgressDataStore by preferencesDataStore(
    name = DATA_STORE_NAME,
)

private object UserProgressKeys {
    val LEVEL = intPreferencesKey("level")
    val CURRENT_XP = intPreferencesKey("current_xp")
    val TOTAL_XP = intPreferencesKey("total_xp")
    val CURRENT_STREAK_DAYS = intPreferencesKey("current_streak_days")
    val LONGEST_STREAK_DAYS = intPreferencesKey("longest_streak_days")
    val LAST_COMPLETION_DAY = intPreferencesKey("last_completion_day")
}

/**
 * Simple XP/level/streak system:
 * - +10 XP por cada hábito completado.
 * - Nivel sube cada 100 XP acumulados.
 * - Racha diaria aumenta si hoy es el día siguiente al último con completados.
 */
class UserProgressRepositoryImpl(
    private val context: Context,
) : UserProgressRepository {

    override fun observeProgress(): Flow<UserProgress> {
        return context.userProgressDataStore.data.map { prefs ->
            val level = prefs[UserProgressKeys.LEVEL] ?: 1
            val currentXp = prefs[UserProgressKeys.CURRENT_XP] ?: 0
            val totalXp = prefs[UserProgressKeys.TOTAL_XP] ?: 0
            val currentStreak = prefs[UserProgressKeys.CURRENT_STREAK_DAYS] ?: 0
            val longestStreak = prefs[UserProgressKeys.LONGEST_STREAK_DAYS] ?: 0
            val xpForNextLevel = 100

            UserProgress(
                level = level,
                currentXp = currentXp,
                xpForNextLevel = xpForNextLevel,
                totalXp = totalXp,
                currentStreakDays = currentStreak,
                longestStreakDays = longestStreak,
            )
        }
    }

    override suspend fun addXp(amount: Int) {
        context.userProgressDataStore.edit { prefs ->
            val currentXp = prefs[UserProgressKeys.CURRENT_XP] ?: 0
            val totalXp = prefs[UserProgressKeys.TOTAL_XP] ?: 0
            val level = prefs[UserProgressKeys.LEVEL] ?: 1
            val xpForLevel = 100

            var newCurrentXp = currentXp + amount
            var newLevel = level
            var newTotalXp = totalXp + amount

            while (newCurrentXp >= xpForLevel) {
                newCurrentXp -= xpForLevel
                newLevel += 1
            }

            prefs[UserProgressKeys.CURRENT_XP] = newCurrentXp
            prefs[UserProgressKeys.TOTAL_XP] = newTotalXp
            prefs[UserProgressKeys.LEVEL] = newLevel
        }
    }

    override suspend fun registerDayWithCompletion() {
        context.userProgressDataStore.edit { prefs ->
            val currentStreak = prefs[UserProgressKeys.CURRENT_STREAK_DAYS] ?: 0
            val longestStreak = prefs[UserProgressKeys.LONGEST_STREAK_DAYS] ?: 0
            val lastDay = prefs[UserProgressKeys.LAST_COMPLETION_DAY]
            val today = LocalDate.now().toEpochDay().toInt()

            val newCurrentStreak = when {
                lastDay == null -> 1
                today - lastDay == 1 -> currentStreak + 1
                today == lastDay -> currentStreak // same day, do not change
                else -> 1
            }

            val newLongestStreak = maxOf(longestStreak, newCurrentStreak)

            prefs[UserProgressKeys.CURRENT_STREAK_DAYS] = newCurrentStreak
            prefs[UserProgressKeys.LONGEST_STREAK_DAYS] = newLongestStreak
            prefs[UserProgressKeys.LAST_COMPLETION_DAY] = today
        }
    }
}


