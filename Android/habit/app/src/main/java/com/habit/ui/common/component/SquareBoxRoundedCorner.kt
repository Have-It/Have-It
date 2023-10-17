package com.habit.ui.common.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.habit.ui.theme.HabitPurpleNormal
import com.habit.ui.theme.HabitTheme
import com.habit.ui.theme.KakaoYellow

@Composable
fun SquareBoxRoundedCorner(
    backgroundColor: Color,
    paddingVertical: Dp = 0.dp,
    paddingHorizontal: Dp = 0.dp,
    elevation: Dp = 0.dp,
    content: @Composable () -> Unit
) {
    Surface(
        color = Color.Transparent,
        elevation = elevation
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = backgroundColor,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(paddingHorizontal, paddingVertical),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            content()
        }
    }

}

@Composable
@Preview
fun SquareBoxPreview() {
    Column() {
        SquareBoxRoundedCorner(
            backgroundColor = HabitPurpleNormal,
            content = {
                MediumRoundButton(text = "test", color = HabitPurpleNormal, textStyle = HabitTheme.typography.mediumBoldTextWhite) {

                }
            }
        )
        Spacer(modifier = Modifier.size(30.dp))
        SquareBoxRoundedCorner(
            backgroundColor = KakaoYellow,
            content = { Row {
                Text(text = "수면중 깸")

                Text(text = "30분")
            }}
        )
    }

}