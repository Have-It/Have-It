package com.habit.ui.screen.home.component.bottomsheet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.habit.domain.model.mission.MissionDto
import com.habit.domain.model.mission.SleepSessionDto
import com.habit.ui.common.model.FreeMissionType
import com.habit.ui.common.model.MissionStateType
import com.habit.ui.common.model.MissionType
import com.habit.ui.screen.home.component.bottomsheet.component.BottomSheetHeader
import com.habit.ui.screen.home.component.bottomsheet.component.SleepBottomSheetBody
import java.time.Duration

private const val TAG = "SleepBottomSheet"

@Composable
fun SleepBottomSheet(
    missionStateType: MissionStateType,
    sleepSessionData: SleepSessionDto,
    missionDto: MissionDto,
    onSuccessButtonClicked: () -> Unit
) {

    Column(modifier = Modifier.padding(20.dp)) {
        BottomSheetHeader(
            missionStateType = missionStateType,
            missionType = MissionType.SLEEP,
            sleepHour = sleepSessionData.totalSleepTime.toHours(),
            sleepMinute = sleepSessionData.totalSleepTime.toMinutes() % 60,
            missionDto = missionDto,
            onSuccessButtonClicked = onSuccessButtonClicked
        )
        Spacer(modifier = Modifier.size(20.dp))
        SleepBottomSheetBody(
            sleepSessionData.awakeTime.toHours(),
            sleepSessionData.awakeTime.toMinutes() % 60,
            sleepSessionData.remTime.toHours(),
            sleepSessionData.remTime.toMinutes() % 60,
            sleepSessionData.lightTime.toHours(),
            sleepSessionData.lightTime.toMinutes() % 60,
            sleepSessionData.deepTime.toHours(),
            sleepSessionData.deepTime.toMinutes() % 60
        )
        Spacer(modifier = Modifier.size(50.dp))
    }
}

@Composable
@Preview
fun PrevSleepBottomSheet() {
    SleepBottomSheet(
        MissionStateType.REWARD_POSSIBLE,
        SleepSessionDto(
            Duration.ZERO, Duration.ZERO, Duration.ZERO, Duration.ZERO,
            Duration.ZERO
        ),
        missionDto = MissionDto(0,30,4,30, FreeMissionType.BOOK.type,"토지 읽기")

    ){

    }
}