package com.habit.ui.screen.home.component.bottomsheet.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.habit.R
import com.habit.ui.common.model.MissionType
import com.habit.ui.theme.HabitPurpleNormal
import com.habit.ui.theme.HabitTheme

@Composable
fun GetCoinButton(
    missionType:MissionType,
    onClick: () -> Unit
) {
    var text = stringResource(id = R.string.home_get_coin_button_text)
    if(missionType == MissionType.FREE){
        text = stringResource(id = R.string.home_get_pet_reward_text)
    }
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(15.dp),
        contentPadding = PaddingValues(5.dp, 10.dp),
        colors = ButtonDefaults.buttonColors(HabitPurpleNormal)
    ) {
        Text(
            text = text,
            style =  HabitTheme.typography.headTextWhite,
            modifier = Modifier.padding(20.dp, 4.dp)
        )
    }
}

@Composable
@Preview
fun PrevGetCoinButton() {
    GetCoinButton(MissionType.FREE) {

    }
}