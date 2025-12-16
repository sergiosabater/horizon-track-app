package com.example.horizontrack.domain.repository

import com.example.horizontrack.domain.model.UserProgress
import kotlinx.coroutines.flow.Flow

/**
 * Stores and exposes aggregated RPG-style user progress (XP, niveles, rachas).
 * Implementado con DataStore pero preparado para extender a nube.
 */
interface UserProgressRepository {

    fun observeProgress(): Flow<UserProgress>

    suspend fun addXp(amount: Int)

    suspend fun registerDayWithCompletion()
}


