package com.habit.util

fun intToStatString(intValue: Int): String = when (intValue) {
    -1 -> "Error"
    in 0..4 -> "Very Good"
    in 5..9 -> "Good"
    in 10..14 -> "Normal"
    in 15..19 -> "Bad"
    else -> "Very Bad"
}
