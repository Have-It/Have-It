package com.habit.ui.screen.home.component.body

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.habit.R
import com.habit.ui.common.component.SquareBoxRoundedCorner
import com.habit.ui.common.component.WhiteVerticalDivider
import com.habit.ui.common.model.FreeMissionType
import com.habit.ui.common.model.MissionStateType
import com.habit.ui.common.model.MissionType
import com.habit.ui.screen.home.SleepPermissionState
import com.habit.ui.theme.HabitPurpleExtraLight3
import com.habit.ui.theme.HabitTheme
import com.habit.util.clickable
import com.habit.util.typeToIcon

@Composable
fun MissionCard(
    permissionState: SleepPermissionState = SleepPermissionState.PermissionAccepted,
    missionType: MissionType,
    totalCalorieData: Int = 0,
    sleepHourData: Long = 0,
    sleepMinuteData: Long = 0,
    freeMissionData: String = "",
    freeMissionType: String = "",
    missionState: MissionStateType,
    missionDescription: String,
    missionStateDescription: String,
    onClicked: () -> Unit
) {
    var missionIcon: Painter = painterResource(id = R.drawable.icon_fire)
    var missionDataText: String = ""

    if (missionType != MissionType.FREE) {
        missionIcon = painterResource(id = missionType.icon)
        when (missionType) {
            MissionType.CALORIE -> {
                missionDataText =
                    stringResource(R.string.home_cal_mission_text, totalCalorieData.toString())
            }

            MissionType.SLEEP -> {
                missionDataText =
                    stringResource(R.string.home_sleep_mission_text, sleepHourData, sleepMinuteData)
            }

            else -> {}
        }
    } else {
        missionIcon = painterResource(id = typeToIcon(freeMissionType))
        missionDataText = freeMissionData
    }

    Box(modifier = Modifier.clickable(onClick = onClicked)) {
        SquareBoxRoundedCorner(
            backgroundColor = HabitPurpleExtraLight3
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.weight(1f))
                Row(
                    modifier = Modifier.weight(14f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        modifier = Modifier.weight(2f),
                        contentScale = ContentScale.Fit,
                        painter = missionIcon,
                        contentDescription = missionDescription
                    )
                    Spacer(modifier = Modifier.size(10.dp))
                    if (permissionState == SleepPermissionState.PermissionNotAccepted) {
                        Text(
                            modifier = Modifier.weight(8f),
                            text = stringResource(id = R.string.home_permission_not_allow),
                            style = HabitTheme.typography.cardTextBlack
                        )
                    } else {
                        Text(
                            modifier = Modifier.weight(8f),
                            text = missionDataText,
                            style = HabitTheme.typography.cardTextBlack
                        )
                    }

                }
                WhiteVerticalDivider(height = 80.dp)
                Spacer(modifier = Modifier.weight(1f))
                Row(
                    modifier = Modifier.weight(2f),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = missionState.icon),
                        contentScale = ContentScale.FillWidth,
                        contentDescription = missionStateDescription
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }

}

@Composable
@Preview
fun PrevMissionCard() {
    Column(verticalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxSize()) {
        MissionCard(
            SleepPermissionState.PermissionAccepted,
            missionType = MissionType.CALORIE,
            totalCalorieData = 350,
            missionState = MissionStateType.REWARD_POSSIBLE,
            missionDescription = "소모 칼로리",
            missionStateDescription = "미션 상태",
            onClicked = {}
        )
        MissionCard(
            SleepPermissionState.PermissionAccepted,
            missionType = MissionType.FREE,
            missionState = MissionStateType.ALREADY_REWARDED,
            freeMissionType = FreeMissionType.BOOK.type,
            freeMissionData = "토지 1권 4장 읽기---",
            missionDescription = "free mission",
            missionStateDescription = "already rewarded",
            onClicked = {}
        )
        MissionCard(
            SleepPermissionState.PermissionAccepted,
            missionType = MissionType.FREE,
            missionState = MissionStateType.NOT_ACHIEVED,
            freeMissionType = FreeMissionType.DIET.type,
            freeMissionData = "세끼 먹기",
            missionDescription = "free mission",
            missionStateDescription = "already rewarded",
            onClicked = {}
        )
        MissionCard(
            SleepPermissionState.PermissionAccepted,
            missionType = MissionType.FREE,
            missionState = MissionStateType.ALREADY_REWARDED,
            freeMissionType = FreeMissionType.WATER.type,
            freeMissionData = "2잔 마시기",
            missionDescription = "free mission",
            missionStateDescription = "already rewarded",
            onClicked = {}
        )
        MissionCard(
            SleepPermissionState.PermissionAccepted,
            missionType = MissionType.FREE,
            missionState = MissionStateType.ALREADY_REWARDED,
            freeMissionType = FreeMissionType.VITAMIN.type,
            freeMissionData = "비타민 C, D 먹기",
            missionDescription = "free mission",
            missionStateDescription = "already rewarded",
            onClicked = {}
        )
        MissionCard(
            SleepPermissionState.PermissionAccepted,
            missionType = MissionType.FREE,
            missionState = MissionStateType.ALREADY_REWARDED,
            freeMissionType = FreeMissionType.CLEANING.type,
            freeMissionData = "매일 책상 청소",
            missionDescription = "free mission",
            missionStateDescription = "already rewarded",
            onClicked = {}
        )
        MissionCard(
            SleepPermissionState.PermissionAccepted,
            missionType = MissionType.FREE,
            missionState = MissionStateType.ALREADY_REWARDED,
            freeMissionType = FreeMissionType.DIARY.type,
            freeMissionData = "일기 2장",
            missionDescription = "free mission",
            missionStateDescription = "already rewarded",
            onClicked = {}
        )
        MissionCard(
            SleepPermissionState.PermissionNotAccepted,
            missionType = MissionType.FREE,
            missionState = MissionStateType.ALREADY_REWARDED,
            freeMissionType = FreeMissionType.STUDY.type,
            freeMissionData = "수학 10문제",
            missionDescription = "free mission",
            missionStateDescription = "already rewarded",
            onClicked = {}
        )
    }

}