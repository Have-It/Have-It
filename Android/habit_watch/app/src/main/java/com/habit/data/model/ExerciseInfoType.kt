package com.habit.data.model

import androidx.annotation.DrawableRes
import com.habit.R

enum class ExerciseInfoType(@DrawableRes val icon: Int, val description: String) {
    CALORIE(R.drawable.icon_flat_fire, "calorie"),
    TIME(R.drawable.icon_flat_time, "time"),
    HEART_REATE(R.drawable.icon_flat_heart, "heart rate")
}