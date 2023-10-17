package com.habit.ui.common.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.habit.R
import com.habit.ui.theme.HabitPurpleExtraLight
import com.habit.ui.theme.HabitPurpleNormal
import com.habit.ui.theme.HabitTheme
import com.habit.util.clickable

@Composable
fun RoundBackText(
    backgroundColor: Color,
    text: String,
    textStyle: TextStyle,
    paddingHorizontal: Dp = 0.dp,
    paddingVertical: Dp = 0.dp
) {
    Box(
        modifier = Modifier
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(100.dp)
            )
            .padding(paddingHorizontal, paddingVertical)
    ) {
        Text(text = text, style = textStyle)
    }
}

@Composable
fun RoundBackTextWithIconClickable(
    backgroundColor: Color,
    text: String,
    textStyle: TextStyle,
    icon: Painter,
    contentDescription: String,
    paddingHorizontal: Dp = 0.dp,
    paddingVertical: Dp = 0.dp,
    onClick: () -> Unit
) {
    Box(modifier = Modifier.clickable { onClick() }) {
        RoundBackTextWithIcon(
            backgroundColor = backgroundColor,
            text = text,
            textStyle = textStyle,
            icon = icon,
            contentDescription = contentDescription,
            paddingHorizontal = paddingHorizontal,
            paddingVertical = paddingVertical
        )
    }
}

@Composable
fun RoundBackTextWithIcon(
    backgroundColor: Color,
    text: String,
    textStyle: TextStyle,
    icon: Painter,
    contentDescription: String,
    paddingHorizontal: Dp = 0.dp,
    paddingVertical: Dp = 0.dp
) {

    Box(contentAlignment = Alignment.CenterStart) {
        Row() {
            Spacer(modifier = Modifier.size(14.dp))
            RoundBackText(
                backgroundColor = backgroundColor,
                text = text,
                textStyle = textStyle,
                paddingHorizontal = paddingHorizontal,
                paddingVertical = paddingVertical
            )
        }
        Image(
            painter = icon, contentDescription = contentDescription
        )
    }

}

@Composable
@Preview
fun PrevRoundBackText() {
    Column() {
        RoundBackText(
            backgroundColor = HabitPurpleExtraLight,
            text = "test1",
            textStyle = HabitTheme.typography.mediumSquareButtonTextKakaoLabel
        )
        Spacer(modifier = Modifier.size(10.dp))
        RoundBackText(
            backgroundColor = HabitPurpleExtraLight,
            text = "test2",
            textStyle = HabitTheme.typography.mediumSquareButtonTextKakaoLabel,
            paddingHorizontal = 10.dp,
            paddingVertical = 10.dp
        )
        Spacer(modifier = Modifier.size(10.dp))
        RoundBackText(
            backgroundColor = HabitPurpleExtraLight,
            text = "test3",
            textStyle = HabitTheme.typography.mediumSquareButtonTextKakaoLabel,
            paddingHorizontal = 20.dp,
            paddingVertical = 4.dp
        )
        Spacer(modifier = Modifier.size(10.dp))
        RoundBackTextWithIcon(
            backgroundColor = HabitPurpleNormal,
            text = "연속 달성 300일",
            textStyle = HabitTheme.typography.mediumBoldTextWhite,
            icon = painterResource(R.drawable.icon_running_shoe),
            contentDescription = "연속달성",
            paddingVertical = 4.dp,
            paddingHorizontal = 20.dp
        )
        Spacer(modifier = Modifier.size(10.dp))
        RoundBackTextWithIcon(
            backgroundColor = HabitPurpleNormal,
            text = "연속 달성 300일",
            textStyle = HabitTheme.typography.mediumBoldTextWhite,
            icon = painterResource(R.drawable.icon_money_bag),
            contentDescription = "연속달성",
            paddingVertical = 50.dp,
            paddingHorizontal = 20.dp
        )
    }
}