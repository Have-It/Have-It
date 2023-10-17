package com.habit.ui.common.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.habit.ui.theme.HabitTheme

@Composable
fun MediumRoundButton(
    text: String,
    color: Color,
    textStyle: TextStyle,
    onClicked: () -> Unit
) {
    Button(
        onClick = onClicked,
        colors = ButtonDefaults.buttonColors(color)
    ) {
        Text(text = text, style = textStyle, modifier = Modifier.padding(20.dp, 4.dp))
    }
}

@Composable
@Preview
fun MediumButtonPreview() {
    MediumRoundButton(
        text = "test text",
        color = Color.Blue,
        textStyle = HabitTheme.typography.boldBodyGray
    ) {}
}