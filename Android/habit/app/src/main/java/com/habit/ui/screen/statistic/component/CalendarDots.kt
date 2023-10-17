package com.habit.ui.screen.statistic.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.habit.ui.theme.HabitColors
import com.habit.ui.theme.HabitTheme

@Composable
fun CalendarDots(){

    Row(
        Modifier.width(120.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Surface(
            shape = RoundedCornerShape(32.dp),
            color = HabitTheme.colors.habitPurpleExtralight,
            modifier = Modifier
                .height(32.dp)
                .width(32.dp),
            ){
        }

        Surface(
            shape = RoundedCornerShape(32.dp),
            color = HabitTheme.colors.habitPurpleLight,
            modifier = Modifier
                .height(32.dp)
                .width(32.dp),
        ){
        }

        Surface(
            shape = RoundedCornerShape(32.dp),
            color = HabitTheme.colors.habitPurpleNormal,
            modifier = Modifier
                .height(32.dp)
                .width(32.dp),
        ){
        }
    }

}

@Preview
@Composable
fun MyPreview(){
    CalendarDots()
}