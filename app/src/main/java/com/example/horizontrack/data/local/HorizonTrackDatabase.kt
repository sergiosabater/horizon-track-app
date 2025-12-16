package com.example.horizontrack.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [
        HabitEntity::class,
        HabitCompletionEntity::class,
    ],
    version = 1,
)
@TypeConverters(LocalDateTypeConverter::class)
abstract class HorizonTrackDatabase : RoomDatabase() {
    abstract fun habitDao(): HabitDao
}


