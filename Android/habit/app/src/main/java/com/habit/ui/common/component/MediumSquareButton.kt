package com.habit.ui.common.component

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.habit.R
import com.habit.ui.theme.HabitTheme

@Composable
fun MediumSquareButton (
    text: String,
    color: Color,
    textStyle: TextStyle,
    leftIcon: Painter,
    leftIconDescription: String,
    onClicked: () -> Unit
) {
    Button(
        onClick = onClicked,
        colors = ButtonDefaults.buttonColors(color),
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.height(56.dp)
    ) {
        Icon(painter = leftIcon, contentDescription = leftIconDescription)
        Text(text = text, style = textStyle, modifier = Modifier.padding(20.dp, 4.dp))
    }
}

@Composable
@Preview
fun MediumSquareButtonPreview() {
    MediumSquareButton(
        text = "test text",
        color = Color.Blue,
        textStyle = HabitTheme.typography.mediumSquareButtonTextKakaoLabel,
        leftIcon = painterResource(id = R.drawable.icon_kakao),
        leftIconDescription = "input get"
    ) {}
}