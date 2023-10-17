package com.habit.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "exercise") //dao에서 사용되는 테이블 이름
data class ExerciseEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long?,
    val userId: Int,
    val exerciseType: String,
    val time: Date,
    val calorie: Int
)