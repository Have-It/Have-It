package com.habit.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.habit.data.entity.ExerciseEntity
import java.util.Date

@Database(
    entities = [ExerciseEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(ExerciseDataBase.RoomDateConverter::class)
abstract class ExerciseDataBase : RoomDatabase() {
    abstract fun exerciseDao(): ExerciseDao

    class RoomDateConverter {
        @TypeConverter
        fun dateToLong(date: Date): Long {
            return date.time
        }

        @TypeConverter
        fun longToDate(time: Long): Date {
            return Date(time)
        }
    }
}