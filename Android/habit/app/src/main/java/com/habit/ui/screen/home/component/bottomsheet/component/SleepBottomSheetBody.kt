package com.habit.ui.screen.home.component.bottomsheet.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.habit.R
import com.habit.ui.common.component.SquareBoxRoundedCorner
import com.habit.ui.common.model.SleepType
import com.habit.ui.theme.HabitBlueLight
import com.habit.ui.theme.HabitPinkLight
import com.habit.ui.theme.HabitPurpleExtraLight3
import com.habit.ui.theme.HabitPurpleLight
import com.habit.ui.theme.HabitPurpleNormal
import com.habit.ui.theme.HabitTheme

@Composable
fun SleepBottomSheetBody(
    awakeHour: Long,
    awakeMinute: Long,
    remHour: Long,
    remMinute: Long,
    lightHour: Long,
    lightMinute: Long,
    deepHour: Long,
    deepMinute: Long
) {
    Column() {
        SleepCard(sleepType = SleepType.AWAKE, sleepHour = awakeHour, sleepMinute = awakeMinute)
        Spacer(modifier = Modifier.size(10.dp))
        SleepCard(sleepType = SleepType.REM, sleepHour = remHour, sleepMinute = remMinute)
        Spacer(modifier = Modifier.size(10.dp))
        SleepCard(sleepType = SleepType.LIGHT, sleepHour = lightHour, sleepMinute = lightMinute)
        Spacer(modifier = Modifier.size(10.dp))
        SleepCard(sleepType = SleepType.DEEP, sleepHour = deepHour, sleepMinute = deepMinute)
        Spacer(modifier = Modifier.size(10.dp))
    }
}

@Composable
fun SleepCard(
    sleepType: SleepType,
    sleepHour: Long,
    sleepMinute: Long
) {
    var cardColor = HabitPurpleExtraLight3
    when (sleepType) {
        SleepType.AWAKE -> cardColor = HabitPinkLight
        SleepType.REM -> cardColor = HabitBlueLight
        SleepType.LIGHT -> cardColor = HabitPurpleLight
        else -> cardColor = HabitPurpleNormal
    }

    SquareBoxRoundedCorner(
        backgroundColor = cardColor,
        paddingHorizontal = 20.dp,
        paddingVertical = 30.dp
    ) {
        Row {
            Text(
                modifier = Modifier
                    .weight(2f)
                    .alignByBaseline()
                    .align(Alignment.Bottom),
                textAlign = TextAlign.Center,
                text = stringResource(id = sleepType.cardText),
                style = HabitTheme.typography.cardTextGray900
            )
            Box(
                modifier = Modifier
                    .background(color = Color.White)
                    .size(2.dp, 25.dp)
            )
            Row(modifier = Modifier.weight(4f)) {
                Spacer(modifier = Modifier.size(20.dp))
                if (sleepHour > 0) {
                    Text(
                        modifier = Modifier.alignByBaseline(),
                        text = stringResource(id = R.string.home_bottom_sheet_card_hour, sleepHour),
                        style = HabitTheme.typography.largeCardTextGray900
                    )
                    Spacer(modifier = Modifier.size(10.dp))
                }
                Text(
                    modifier = Modifier.alignByBaseline(),
                    text = stringResource(id = R.string.home_bottom_sheet_card_min, sleepMinute),
                    style = HabitTheme.typography.largeCardTextGray900
                )
            }
        }
    }
}

@Composable
@Preview
fun PrevSleepCard() {
    SleepCard(
        sleepType = SleepType.LIGHT,
        sleepHour = 1,
        sleepMinute = 30
    )
}