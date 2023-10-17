package com.habit.ui.common.model

import androidx.annotation.DrawableRes
import androidx.compose.ui.res.painterResource
import com.habit.R

enum class FreeMissionType(val text: String, @DrawableRes val icon: Int, val type: String) {
    BOOK("독서하기", R.drawable.icon_open_book, "book"),
    DIET("식단 관리", R.drawable.icon_diet, "diet"),
    WATER("물 마시기", R.drawable.icon_water, "water"),
    VITAMIN("영양제 먹기", R.drawable.icon_pill, "vitamin"),
    CLEANING("청소하기", R.drawable.icon_clean, "cleaning"),
    DIARY("일기 작성", R.drawable.icon_diary, "diary"),
    STUDY("공부", R.drawable.icon_study, "study");

}