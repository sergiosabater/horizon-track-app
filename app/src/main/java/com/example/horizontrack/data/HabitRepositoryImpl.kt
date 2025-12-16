package com.example.horizontrack.data

import com.example.horizontrack.data.local.HabitDao
import com.example.horizontrack.domain.model.Habit
import com.example.horizontrack.domain.model.HabitCompletion
import com.example.horizontrack.domain.model.HabitProgress
import com.example.horizontrack.domain.repository.HabitRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import java.time.LocalDate

class HabitRepositoryImpl(
    private val habitDao: HabitDao,
) : HabitRepository {

    override fun observeHabitsWithProgress(
        referenceDate: LocalDate,
    ): Flow<List<HabitProgress>> {
        return habitDao.observeHabits().map { entities ->
            entities.map { habitEntity ->
                val habit = habitEntity.toDomain()
                HabitProgress(
                    habit = habit,
                    completions = emptyList(),
                    currentStreak = 0,
                    longestStreak = 0,
                )
            }
        }
    }

    override fun observeHabitProgress(habitId: Long): Flow<HabitProgress?> {
        return combine(
            habitDao.observeHabits(),
            habitDao.observeCompletionsForHabit(habitId),
        ) { habits, completions ->
            val habit = habits.firstOrNull { it.id == habitId }?.toDomain() ?: return@combine null
            val completionDates = completions.map { it.date }
            val (currentStreak, longestStreak) = computeStreaks(completionDates)
            HabitProgress(
                habit = habit,
                completions = completionDates,
                currentStreak = currentStreak,
                longestStreak = longestStreak,
            )
        }
    }

    override suspend fun createHabit(habit: Habit): Long {
        return habitDao.insertHabit(habit.toEntity())
    }

    override suspend fun updateHabit(habit: Habit) {
        habitDao.updateHabit(habit.toEntity())
    }

    override suspend fun toggleCompletion(habitId: Long, date: LocalDate) {
        val existing = habitDao.getCompletionForDate(habitId, date)
        if (existing == null) {
            habitDao.insertCompletion(
                HabitCompletion(
                    habitId = habitId,
                    date = date,
                ).toEntity(),
            )
        } else {
            habitDao.deleteCompletionById(existing.id)
        }
    }

    override suspend fun getCompletionForDate(
        habitId: Long,
        date: LocalDate,
    ): HabitCompletion? {
        return habitDao.getCompletionForDate(habitId, date)?.toDomain()
    }

    private fun computeStreaks(completions: List<LocalDate>): Pair<Int, Int> {
        if (completions.isEmpty()) return 0 to 0
        val sorted = completions.sorted()
        var longest = 1
        var current = 1

        for (i in 1 until sorted.size) {
            val prev = sorted[i - 1]
            val curr = sorted[i]
            if (curr.toEpochDay() - prev.toEpochDay() == 1L) {
                current++
                if (current > longest) longest = current
            } else {
                current = 1
            }
        }
        // For simplicity we treat current == longest when equal
        return current to longest
    }
}


