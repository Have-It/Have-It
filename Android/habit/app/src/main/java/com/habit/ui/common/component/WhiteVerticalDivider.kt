package com.habit.ui.common.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun WhiteVerticalDivider(height: Dp) {
    Box(
        modifier = Modifier
            .background(color = Color.White)
            .width(2.dp)
            .height(height),
    )
}