package com.habit.domain.model.mission

import java.util.Date


data class ExerciseDto (
    val id: Long = -1,
    val userId: Int = -1,
    val exerciseType: String,
    val time: Date,
    val calorie: Int
)