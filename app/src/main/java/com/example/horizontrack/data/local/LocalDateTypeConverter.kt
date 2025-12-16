package com.example.horizontrack.data.local

import androidx.room.TypeConverter
import java.time.LocalDate

class LocalDateTypeConverter {

    @TypeConverter
    fun fromEpochDay(value: Long?): LocalDate? =
        value?.let { LocalDate.ofEpochDay(it) }

    @TypeConverter
    fun toEpochDay(date: LocalDate?): Long? =
        date?.toEpochDay()
}


