package com.habit.ui.screen.home.component.body

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.habit.domain.model.mission.MissionDto
import com.habit.ui.common.model.MissionStateType
import com.habit.ui.common.model.MissionSuccessData
import com.habit.ui.common.model.MissionType
import com.habit.ui.screen.home.SleepPermissionState
import com.habit.ui.theme.HabitTheme
import com.habit.ui.theme.HabitWhite
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private const val TAG = "BodyComponent"

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BodyComponent(
    missionSuccessData: MissionSuccessData,
    missionData: MissionDto,
    totalCalorieData: Int,
    sleepPermissionState: SleepPermissionState,
    sleepData: Duration,
    onCalorieMissionCardClicked: () -> Unit,
    onSleepMissionCardClicked: () -> Unit,
    onFreeMissionCardClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = HabitWhite, shape = RoundedCornerShape(40.dp, 40.dp, 0.dp, 0.dp))
            .padding(24.dp, 0.dp)
    ) {
        Spacer(modifier = Modifier.size(30.dp))
        TodaysDate()
        MissionCards(
            missionSuccessData,
            missionData,
            totalCalorieData,
            sleepPermissionState,
            sleepData,
            onCalorieMissionCardClicked,
            onSleepMissionCardClicked,
            onFreeMissionCardClicked
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TodaysDate() {
    val current = LocalDateTime.now()
    val formatter = DateTimeFormatter.ofPattern("MM월 dd일")
    val formatted = current.format(formatter)
    Text(text = "${formatted}", style = HabitTheme.typography.mediumBoldTextBlack)
}

@Composable
fun MissionCards(
    missionSuccessData: MissionSuccessData,
    missionData: MissionDto,
    totalCalorieData: Int,
    sleepPermissionState: SleepPermissionState,
    sleepData: Duration,
    onCalorieMissionCardClicked: () -> Unit,
    onSleepMissionCardClicked: () -> Unit,
    onFreeMissionCardClicked: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        MissionCard(
            missionType = MissionType.CALORIE,
            missionState = missionSuccessData.calorieMissionSuccessState,
            totalCalorieData = totalCalorieData,
            missionDescription = "calorie mission",
            missionStateDescription = missionSuccessData.calorieMissionSuccessState.description,
            onClicked = onCalorieMissionCardClicked
        )
        MissionCard(
            permissionState = sleepPermissionState,
            missionType = MissionType.SLEEP,
            missionState = missionSuccessData.sleepMissionSuccessState,
            missionDescription = "sleep mission",
            missionStateDescription = missionSuccessData.sleepMissionSuccessState.description,
            sleepHourData = sleepData.toHours(),
            sleepMinuteData = sleepData.toMinutes() % 60,
            onClicked = onSleepMissionCardClicked
        )


        MissionCard(
            missionType = MissionType.FREE,
            missionState = missionSuccessData.freeMissionSuccessState,
            missionDescription = "free mission",
            missionStateDescription = missionSuccessData.freeMissionSuccessState.description,
            freeMissionType = missionData.freeMissionType,
            freeMissionData = missionData.freeMissionDetail,
            onClicked = onFreeMissionCardClicked
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
@Preview
fun PrevBodyComponent() {
    BodyComponent(
        MissionSuccessData(MissionStateType.REWARD_POSSIBLE, MissionStateType.ALREADY_REWARDED, MissionStateType.NOT_ACHIEVED),
        MissionDto(0, 23, 1, 23, "book", "토지읽기"),
        0,
        SleepPermissionState.PermissionAccepted,
        Duration.ZERO,
        {},
        {},
        {}
    )
}