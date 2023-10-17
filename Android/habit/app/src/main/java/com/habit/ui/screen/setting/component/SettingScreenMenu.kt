package com.habit.ui.screen.setting.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.habit.R
import com.habit.ui.theme.HabitTheme
import com.habit.util.clickable

@Composable
fun SettingScreenMenu(
    menuText: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier.clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(painter = painterResource(id = R.drawable.icon_laptop), contentDescription = null)
        Spacer(modifier = Modifier.size(10.dp))
        Text(text = menuText, style = HabitTheme.typography.mediumNormalTextGray700)
    }

}

@Composable
@Preview
fun PrevSettingScreenMenu() {
    SettingScreenMenu(menuText = "미션 수정하기") {

    }
}