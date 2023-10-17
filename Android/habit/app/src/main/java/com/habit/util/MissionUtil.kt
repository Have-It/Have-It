package com.habit.util

import com.habit.R
import com.habit.ui.common.model.FreeMissionType
import com.habit.ui.common.model.HabitExerciseType

private const val TAG = "MissionUtil"

// 최소 칼로리인지 확인한다
fun checkMinimumCalorie(calorie: Int): Boolean {
    return calorie >= 5 //최소 5칼로리
}

// 최소 수면 시간인지 확인한다
fun checkMinimumSleep(hour: Int): Boolean {
    return hour >= 1
}

// 입력 수면 값이 올바른지 확인한다
fun checkSleepValue(minute: Int): Boolean {
    return minute <= 59
}

fun typeToIcon(type: String): Int = when (type) {
    FreeMissionType.BOOK.type -> R.drawable.icon_open_book
    FreeMissionType.DIET.type -> R.drawable.icon_diet
    FreeMissionType.WATER.type -> R.drawable.icon_water
    FreeMissionType.VITAMIN.type -> R.drawable.icon_pill
    FreeMissionType.CLEANING.type -> R.drawable.icon_clean
    FreeMissionType.DIARY.type -> R.drawable.icon_diary
    FreeMissionType.STUDY.type -> R.drawable.icon_study
    else -> {
        R.drawable.icon_open_book
    }
}

fun stringToFreeMissionType(string: String): FreeMissionType = when (string) {
    FreeMissionType.BOOK.type -> FreeMissionType.BOOK
    FreeMissionType.DIET.type -> FreeMissionType.DIET
    FreeMissionType.WATER.type -> FreeMissionType.WATER
    FreeMissionType.VITAMIN.type -> FreeMissionType.VITAMIN
    FreeMissionType.CLEANING.type -> FreeMissionType.CLEANING
    FreeMissionType.DIARY.type -> FreeMissionType.DIARY
    FreeMissionType.STUDY.type -> FreeMissionType.STUDY
    else -> FreeMissionType.BOOK
}

fun stringToExerciseType(exerciseString: String) = when (exerciseString) {
    HabitExerciseType.WALK.exerciseString -> HabitExerciseType.WALK
    HabitExerciseType.GOLF.exerciseString -> HabitExerciseType.GOLF
    HabitExerciseType.BASKETBALL.exerciseString -> HabitExerciseType.BASKETBALL
    HabitExerciseType.RUN.exerciseString -> HabitExerciseType.RUN
    HabitExerciseType.HIKING.exerciseString -> HabitExerciseType.HIKING
    HabitExerciseType.TREADMILL.exerciseString -> HabitExerciseType.TREADMILL
    HabitExerciseType.MEDITATION.exerciseString -> HabitExerciseType.MEDITATION
    HabitExerciseType.MARTIAL_ARTS.exerciseString -> HabitExerciseType.MARTIAL_ARTS
    HabitExerciseType.VOLLEYBALL.exerciseString -> HabitExerciseType.VOLLEYBALL
    HabitExerciseType.BADMINTON.exerciseString -> HabitExerciseType.BADMINTON
    HabitExerciseType.SWIM.exerciseString -> HabitExerciseType.SWIM
    HabitExerciseType.SQUAT.exerciseString -> HabitExerciseType.SQUAT
    HabitExerciseType.ROCK_CLIMBING.exerciseString -> HabitExerciseType.ROCK_CLIMBING
    HabitExerciseType.BICYCLE.exerciseString -> HabitExerciseType.BICYCLE
    HabitExerciseType.SKIPPING_ROPE.exerciseString -> HabitExerciseType.SKIPPING_ROPE
    HabitExerciseType.SOCCER.exerciseString -> HabitExerciseType.SOCCER
    HabitExerciseType.DANCE.exerciseString -> HabitExerciseType.DANCE
    HabitExerciseType.TENNIS.exerciseString -> HabitExerciseType.TENNIS
    else -> {
        HabitExerciseType.WALK
    }
}
