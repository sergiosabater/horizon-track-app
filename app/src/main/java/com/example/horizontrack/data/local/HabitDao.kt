package com.example.horizontrack.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface HabitDao {

    @Query("SELECT * FROM habits WHERE isArchived = 0 ORDER BY id DESC")
    fun observeHabits(): Flow<List<HabitEntity>>

    @Query(
        """
        SELECT * FROM habit_completions 
        WHERE habitId = :habitId
        """
    )
    fun observeCompletionsForHabit(habitId: Long): Flow<List<HabitCompletionEntity>>

    @Query(
        """
        SELECT * FROM habit_completions 
        WHERE habitId = :habitId AND date = :date
        LIMIT 1
        """
    )
    suspend fun getCompletionForDate(habitId: Long, date: LocalDate): HabitCompletionEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHabit(habit: HabitEntity): Long

    @Update
    suspend fun updateHabit(habit: HabitEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCompletion(completion: HabitCompletionEntity): Long

    @Query("DELETE FROM habit_completions WHERE id = :id")
    suspend fun deleteCompletionById(id: Long)
}


