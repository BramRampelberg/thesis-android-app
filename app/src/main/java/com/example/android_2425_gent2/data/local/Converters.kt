package com.example.android_2425_gent2.data.local

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.LocalTime

class Converters {
    @TypeConverter
    fun fromLocalDateLong(value: Long?): LocalDate? {
        return value?.let { LocalDate.ofEpochDay(it) }
    }

    @TypeConverter
    fun localDateToLong(localDate: LocalDate?): Long? {
        return localDate?.toEpochDay()
    }

    @TypeConverter
    fun fromLocalTimeLong(value: Long?): LocalTime? {
        return value?.let { LocalTime.ofNanoOfDay(it) }
    }

    @TypeConverter
    fun localTimeToLong(localTime: LocalTime?): Long? {
        return localTime?.toNanoOfDay()
    }
}