package com.habit.data.repository.datasource

import com.habit.data.entity.ExerciseEntity
import java.util.Date

interface LocalDataSource {
    suspend fun insertExercise(exerciseEntity: ExerciseEntity)
    suspend fun selectAll(): List<ExerciseEntity>
    suspend fun selectTodaysCalorie(begin: Date, end: Date): Int
    suspend fun selectTodaysExercise(begin: Date, end: Date): List<ExerciseEntity>
}