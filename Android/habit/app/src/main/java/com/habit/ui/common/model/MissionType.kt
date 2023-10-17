package com.habit.ui.common.model

import androidx.annotation.DrawableRes
import com.habit.R

enum class MissionType(val description: String, @DrawableRes val icon: Int) {
    CALORIE("칼로리", R.drawable.icon_fire),
    SLEEP("수면", R.drawable.icon_flat_moon),
    FREE("자유", R.drawable.icon_open_book)
}