package com.example.horizontrack.data

import com.example.horizontrack.data.local.HabitCompletionEntity
import com.example.horizontrack.data.local.HabitEntity
import com.example.horizontrack.domain.model.Habit
import com.example.horizontrack.domain.model.HabitCompletion
import java.time.DayOfWeek
import java.time.LocalDate

private const val DAYS_IN_WEEK = 7

fun HabitEntity.toDomain(): Habit =
    Habit(
        id = id,
        name = name,
        description = description,
        targetPerWeek = targetPerWeek,
        activeDays = activeDaysBitmask.toDayOfWeekSet(),
        colorHex = colorHex,
        isArchived = isArchived,
    )

fun Habit.toEntity(): HabitEntity =
    HabitEntity(
        id = id,
        name = name,
        description = description,
        targetPerWeek = targetPerWeek,
        activeDaysBitmask = activeDays.toBitmask(),
        colorHex = colorHex,
        isArchived = isArchived,
    )

fun HabitCompletionEntity.toDomain(): HabitCompletion =
    HabitCompletion(
        id = id,
        habitId = habitId,
        date = date,
    )

fun HabitCompletion.toEntity(): HabitCompletionEntity =
    HabitCompletionEntity(
        id = id,
        habitId = habitId,
        date = date,
    )

private fun Int.toDayOfWeekSet(): Set<DayOfWeek> =
    DayOfWeek.entries
        .filter { day ->
            val bit = 1 shl ((day.ordinal) % DAYS_IN_WEEK)
            this and bit != 0
        }
        .toSet()

private fun Set<DayOfWeek>.toBitmask(): Int =
    this.fold(0) { acc, day ->
        val bit = 1 shl (day.ordinal % DAYS_IN_WEEK)
        acc or bit
    }


