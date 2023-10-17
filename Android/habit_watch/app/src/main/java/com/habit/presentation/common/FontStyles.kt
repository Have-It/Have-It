package com.habit.presentation.common

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

object FontStyles {
    val mediumBold: TextStyle = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold
    )
    val mediumNormal: TextStyle = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal
    )
    val smallNormal: TextStyle = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal
    )
    val smallBold: TextStyle = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold
    )
    val largeNormal: TextStyle = TextStyle(
        fontSize = 20.sp,
        fontWeight = FontWeight.Normal
    )
}