package com.example.horizontrack.domain.model

import java.time.DayOfWeek
import java.time.LocalDate

/**
 * Core habit definition used across the app.
 */
data class Habit(
    val id: Long = 0L,
    val name: String,
    val description: String,
    val targetPerWeek: Int,
    val activeDays: Set<DayOfWeek>,
    val colorHex: Long,
    val isArchived: Boolean = false,
)

/**
 * Completion of a habit for a specific calendar date.
 */
data class HabitCompletion(
    val id: Long = 0L,
    val habitId: Long,
    val date: LocalDate,
)

/**
 * Aggregated progress for a single habit, used for UI.
 */
data class HabitProgress(
    val habit: Habit,
    val completions: List<LocalDate>,
    val currentStreak: Int,
    val longestStreak: Int,
)

/**
 * Global user progress used to drive RPG-style gamification.
 */
data class UserProgress(
    val level: Int,
    val currentXp: Int,
    val xpForNextLevel: Int,
    val totalXp: Int,
    val currentStreakDays: Int,
    val longestStreakDays: Int,
)

/**
 * Lightweight achievement model – can be expanded and synced later.
 */
data class Achievement(
    val id: String,
    val title: String,
    val description: String,
    val unlockedAt: LocalDate?,
)


