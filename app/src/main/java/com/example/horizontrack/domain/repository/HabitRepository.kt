package com.example.horizontrack.domain.repository

import com.example.horizontrack.domain.model.Habit
import com.example.horizontrack.domain.model.HabitCompletion
import com.example.horizontrack.domain.model.HabitProgress
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

/**
 * Abstraction for all habit-related persistence and queries.
 * This makes it easy to swap local/remote implementations later.
 */
interface HabitRepository {

    fun observeHabitsWithProgress(
        referenceDate: LocalDate,
    ): Flow<List<HabitProgress>>

    fun observeHabitProgress(
        habitId: Long,
    ): Flow<HabitProgress?>

    suspend fun createHabit(habit: Habit): Long

    suspend fun updateHabit(habit: Habit)

    suspend fun toggleCompletion(habitId: Long, date: LocalDate)

    suspend fun getCompletionForDate(habitId: Long, date: LocalDate): HabitCompletion?
}


