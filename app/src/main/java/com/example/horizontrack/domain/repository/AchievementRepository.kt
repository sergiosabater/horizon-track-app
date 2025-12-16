package com.example.horizontrack.domain.repository

import com.example.horizontrack.domain.model.Achievement
import kotlinx.coroutines.flow.Flow

/**
 * Repository for managing achievements.
 * Achievements are unlocked automatically based on user progress.
 */
interface AchievementRepository {
    fun observeAchievements(): Flow<List<Achievement>>
    suspend fun checkAndUnlockAchievements(userProgress: com.example.horizontrack.domain.model.UserProgress)
}

