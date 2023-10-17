package com.habit.data.repository.datasourceImpl

import com.habit.data.db.ExerciseDao
import com.habit.data.entity.ExerciseEntity
import com.habit.data.repository.datasource.LocalDataSource
import java.util.Date
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val exerciseDao: ExerciseDao
) : LocalDataSource {
    override suspend fun insertExercise(exerciseEntity: ExerciseEntity) {
        exerciseDao.insertExercise(exerciseEntity)
    }

    override suspend fun selectAll(): List<ExerciseEntity> {
        return exerciseDao.selectAll()
    }

    override suspend fun selectTodaysCalorie(begin: Date, end: Date): Int {
        val totalCalorie = exerciseDao.getTodaysCalorie(begin, end)
        if(totalCalorie == null){
            return 0
        }else{
            return totalCalorie
        }
    }

    override suspend fun selectTodaysExercise(begin: Date, end: Date): List<ExerciseEntity> {
        return exerciseDao.getTodaysExercise(begin, end)
    }

}