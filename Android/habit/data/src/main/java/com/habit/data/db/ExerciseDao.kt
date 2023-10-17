package com.habit.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.habit.data.entity.ExerciseEntity
import java.util.Date

@Dao
interface ExerciseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExercise(exercise: ExerciseEntity)

    @Query("select * from exercise")
    suspend fun selectAll(): List<ExerciseEntity>

    // 당일 3시 ~ 다음날 2시 50분 까지의 총 칼로리를 조회
    @Query("select sum(calorie) from exercise where :begin <= time AND time<= :end")
    suspend fun getTodaysCalorie(begin: Date, end: Date): Int?

    // 당일 3시 ~ 다음날 2시 50분 까지의 운동들을 조회
    @Query("select * from exercise where :begin <= time AND time<= :end")
    suspend fun getTodaysExercise(begin: Date, end: Date): List<ExerciseEntity>

}