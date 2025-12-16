package com.example.horizontrack.data

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.horizontrack.domain.model.Achievement
import com.example.horizontrack.domain.model.UserProgress
import com.example.horizontrack.domain.repository.AchievementRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate

private const val DATA_STORE_NAME = "achievements"

val Context.achievementsDataStore by preferencesDataStore(
    name = DATA_STORE_NAME,
)

private object AchievementKeys {
    val UNLOCKED_IDS = stringSetPreferencesKey("unlocked_ids")
}

/**
 * Simple achievement system with predefined achievements.
 * Achievements are unlocked automatically when conditions are met.
 */
class AchievementRepositoryImpl(
    private val context: Context,
) : AchievementRepository {

    companion object {
        private val ALL_ACHIEVEMENTS = listOf(
            Achievement(
                id = "first_completion",
                title = "First Steps",
                description = "Complete your first habit",
                unlockedAt = null,
            ),
            Achievement(
                id = "streak_3",
                title = "On Fire",
                description = "Maintain a 3-day streak",
                unlockedAt = null,
            ),
            Achievement(
                id = "streak_7",
                title = "Week Warrior",
                description = "Maintain a 7-day streak",
                unlockedAt = null,
            ),
            Achievement(
                id = "streak_30",
                title = "Month Master",
                description = "Maintain a 30-day streak",
                unlockedAt = null,
            ),
            Achievement(
                id = "level_5",
                title = "Rising Star",
                description = "Reach level 5",
                unlockedAt = null,
            ),
            Achievement(
                id = "level_10",
                title = "Habit Hero",
                description = "Reach level 10",
                unlockedAt = null,
            ),
            Achievement(
                id = "level_20",
                title = "Legend",
                description = "Reach level 20",
                unlockedAt = null,
            ),
        )
    }

    override fun observeAchievements(): Flow<List<Achievement>> {
        return context.achievementsDataStore.data.map { prefs ->
            val unlockedIds = prefs[AchievementKeys.UNLOCKED_IDS] ?: emptySet()
            ALL_ACHIEVEMENTS.map { achievement ->
                achievement.copy(
                    unlockedAt = if (unlockedIds.contains(achievement.id)) {
                        LocalDate.now() // In a real app, store the actual unlock date
                    } else null,
                )
            }
        }
    }

    override suspend fun checkAndUnlockAchievements(userProgress: UserProgress) {
        context.achievementsDataStore.edit { prefs ->
            val unlockedIds = prefs[AchievementKeys.UNLOCKED_IDS]?.toMutableSet() ?: mutableSetOf()

            // Check streak achievements
            if (userProgress.currentStreakDays >= 3 && !unlockedIds.contains("streak_3")) {
                unlockedIds.add("streak_3")
            }
            if (userProgress.currentStreakDays >= 7 && !unlockedIds.contains("streak_7")) {
                unlockedIds.add("streak_7")
            }
            if (userProgress.currentStreakDays >= 30 && !unlockedIds.contains("streak_30")) {
                unlockedIds.add("streak_30")
            }

            // Check level achievements
            if (userProgress.level >= 5 && !unlockedIds.contains("level_5")) {
                unlockedIds.add("level_5")
            }
            if (userProgress.level >= 10 && !unlockedIds.contains("level_10")) {
                unlockedIds.add("level_10")
            }
            if (userProgress.level >= 20 && !unlockedIds.contains("level_20")) {
                unlockedIds.add("level_20")
            }

            prefs[AchievementKeys.UNLOCKED_IDS] = unlockedIds
        }
    }
}

