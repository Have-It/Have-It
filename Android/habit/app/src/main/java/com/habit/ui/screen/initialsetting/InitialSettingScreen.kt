package com.habit.ui.screen.initialsetting

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.habit.R
import com.habit.ui.common.component.MediumRoundButton
import com.habit.ui.common.component.SquareBoxRoundedCorner
import com.habit.ui.common.model.FreeMissionType
import com.habit.ui.navigation.BottomNav
import com.habit.ui.navigation.HealthConnectSettingNav
import com.habit.ui.navigation.InitialSettingNav
import com.habit.ui.navigation.LoginNav
import com.habit.ui.screen.MainViewModel
import com.habit.ui.screen.initialsetting.component.ChipList
import com.habit.ui.theme.HabitPurpleExtraLight
import com.habit.ui.theme.HabitPurpleLight
import com.habit.ui.theme.HabitPurpleNormal
import com.habit.ui.theme.HabitTheme
import com.habit.util.checkMinimumCalorie
import com.habit.util.checkMinimumSleep
import com.habit.util.checkSleepValue
import kotlin.math.log

private const val TAG = "InitialSettingScreen"
@Composable
fun InitialSettingScreen(
    navController: NavHostController,
    viewModel: InitialSettingViewModel = hiltViewModel(),
    mainViewModel: MainViewModel = hiltViewModel()
) {
    Log.d(TAG, "InitialSettingScreen: 이니셜")
    val uiState by mainViewModel.initialSettingUiState.collectAsState()
    val context: Context = LocalContext.current
    val freeMissionList = listOf<FreeMissionType>(
        FreeMissionType.BOOK,
        FreeMissionType.DIET,
        FreeMissionType.WATER,
        FreeMissionType.VITAMIN,
        FreeMissionType.CLEANING,
        FreeMissionType.DIARY,
        FreeMissionType.STUDY
    )
    val memberInfo by mainViewModel.memberInfo.collectAsState()
    val missionInfo by mainViewModel.missionInfo.collectAsState()

    var calorieString by remember { mutableStateOf("") }
    var hourString by remember { mutableStateOf("") }
    var minuteString by remember { mutableStateOf("") }
    var freeMissionString by remember { mutableStateOf("") }
    var selectedFreeMission by remember { mutableStateOf(FreeMissionType.BOOK) }

    LaunchedEffect(uiState){
        if(uiState == InitialSettingUiState.MissionExist){
            navController.navigate(HealthConnectSettingNav.route) {
                popUpTo(InitialSettingNav.route) { inclusive = true }
            }
        }
    }

    LaunchedEffect(Unit){
        mainViewModel.checkMissionUserId()
    }

    if(uiState == InitialSettingUiState.MissionNotExist){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = HabitPurpleLight)
                .padding(20.dp, 60.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.initial_setting_title),
                style = HabitTheme.typography.headTextPurpleNormal
            )
            Spacer(modifier = Modifier.size(30.dp))
            CalorieGoalTextField(calorieString = calorieString, onChange = { it -> calorieString = it })
            Spacer(modifier = Modifier.size(35.dp))
            SleepGoalTextField(
                hourString = hourString,
                minuteString = minuteString,
                onHourChange = { it -> hourString = it },
                onMinuteChange = { it -> minuteString = it })
            Spacer(modifier = Modifier.size(35.dp))
            FreeMissionSelecter(
                freeMissionList = freeMissionList,
                freeMissionString = freeMissionString,
                onChange = {
                    freeMissionString = it
                },
                selectedFreeMissionType = selectedFreeMission,
                onFreeMissionSelected = {
                    selectedFreeMission = it
                }
            )
            Spacer(modifier = Modifier.size(60.dp))
            MediumRoundButton(
                text = stringResource(id = R.string.initial_setting_ok_button_text),
                color = HabitPurpleNormal,
                textStyle = HabitTheme.typography.mediumBoldTextWhite
            ) {
                if(calorieString == "" || minuteString == "" || hourString == ""){
                    Toast.makeText(context, "모든 입력창을 채워주세요!", Toast.LENGTH_LONG).show()
                }else if (!checkMinimumCalorie(calorieString.toInt())) { // 최소 칼로리 미만
                    Toast.makeText(context, "최소 5칼로리 이상을 설정해야 합니다", Toast.LENGTH_LONG).show()
                } else if (!checkMinimumSleep(hourString.toInt())) { // 최소 수면 시간 미만
                    Toast.makeText(context, "최소 1시간 이상을 설정해야 합니다", Toast.LENGTH_LONG).show()
                } else if (!checkSleepValue(minuteString.toInt())) { // 수면 시간 값 이상할때
                    Toast.makeText(context, "0 ~ 59분 까지 입력 가능합니다", Toast.LENGTH_LONG).show()
                } else if (freeMissionString == "") { // 자유미션 설정값 없는경우
                    Toast.makeText(context, "자유미션 설정값을 넣어주세요!", Toast.LENGTH_LONG).show()
                } else {
                    viewModel.putMission(
                        memberInfo.memberId,
                        calorieString.toInt(),
                        hourString.toInt(),
                        minuteString.toInt(),
                        selectedFreeMission,
                        freeMissionString
                    )
                    navController.navigate(HealthConnectSettingNav.route) {
                        popUpTo(InitialSettingNav.route) { inclusive = true }
                    }
                }
            }
        }
    }

}

@Composable
fun CalorieGoalTextField(
    calorieString: String,
    onChange: (newText: String) -> Unit
) {
    Column {
        Text(
            text = stringResource(id = R.string.initial_setting_calorie_goal),
            style = HabitTheme.typography.boldBodyPurpleNormal
        )
        Spacer(modifier = Modifier.size(20.dp))
        SquareBoxRoundedCorner(
            backgroundColor = HabitPurpleExtraLight,
            paddingHorizontal = 20.dp,
            paddingVertical = 24.dp
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                BasicTextField(
                    singleLine = true,
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    value = calorieString,
                    textStyle = HabitTheme.typography.headTextPurpleNormal.copy(textAlign = TextAlign.End),
                    onValueChange = { changedString ->
                        Log.d(TAG, "CalorieGoalTextField: ${changedString}")
                        if (changedString.isDigitsOnly()) {
                            onChange(changedString)
                        }
                    },
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.size(4.dp))
                Text(
                    text = stringResource(id = R.string.home_bottom_sheet_kcal),
                    style = HabitTheme.typography.headTextPurpleNormal
                )
            }
        }
    }
}

@Composable
fun SleepGoalTextField(
    hourString: String,
    minuteString: String,
    onHourChange: (newText: String) -> Unit,
    onMinuteChange: (newText: String) -> Unit
) {
    Column {
        Text(
            text = stringResource(id = R.string.initial_setting_sleep_goal),
            style = HabitTheme.typography.boldBodyPurpleNormal
        )
        Spacer(modifier = Modifier.size(20.dp))
        SquareBoxRoundedCorner(
            backgroundColor = HabitPurpleExtraLight,
            paddingHorizontal = 20.dp,
            paddingVertical = 24.dp
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                BasicTextField(
                    singleLine = true,
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    value = hourString,
                    textStyle = HabitTheme.typography.headTextPurpleNormal.copy(textAlign = TextAlign.End),
                    onValueChange = { changedString ->
                        if (changedString.isDigitsOnly()) {
                            onHourChange(changedString)
                        }
                    },
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.size(4.dp))
                Text(
                    text = stringResource(id = R.string.home_bottom_sheet_hour),
                    style = HabitTheme.typography.headTextPurpleNormal
                )
                BasicTextField(
                    singleLine = true,
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    value = minuteString,
                    textStyle = HabitTheme.typography.headTextPurpleNormal.copy(textAlign = TextAlign.End),
                    onValueChange = { changedString ->
                        if (changedString.isDigitsOnly()) {
                            onMinuteChange(changedString)
                        }
                    },
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.size(4.dp))
                Text(
                    text = stringResource(id = R.string.home_bottom_sheet_min),
                    style = HabitTheme.typography.headTextPurpleNormal
                )
            }
        }
    }
}

@Composable
fun FreeMissionSelecter(
    freeMissionList: List<FreeMissionType>,
    freeMissionString: String,
    onChange: (String) -> Unit,
    selectedFreeMissionType: FreeMissionType,
    onFreeMissionSelected: (FreeMissionType) -> Unit
) {

    Column() {
        ChipList(chips = freeMissionList, onChipClick = onFreeMissionSelected)
        Spacer(modifier = Modifier.size(20.dp))
        SquareBoxRoundedCorner(
            backgroundColor = HabitPurpleExtraLight,
            paddingHorizontal = 20.dp,
            paddingVertical = 24.dp
        ) {
            BasicTextField(
                singleLine = true,
                maxLines = 1,
                modifier = Modifier.fillMaxWidth(),
                decorationBox = { innerTextField ->
                    Box() {
                        if (freeMissionString.isEmpty()) {
                            Text(
                                style = HabitTheme.typography.headTextPurpleLight.copy(textAlign = TextAlign.Start),
                                text = stringResource(
                                    id = R.string.inital_setting_free_goal_description
                                ),
                            )
                        }
                        innerTextField()
                    }
                },
                value = freeMissionString,
                textStyle = HabitTheme.typography.headTextPurpleNormal.copy(textAlign = TextAlign.Start),
                onValueChange = { changedString ->
                    onChange(changedString)
                },
            )
        }
    }

}


@Composable
@Preview
fun PrevInitialSettingScreen() {
    val navController = rememberNavController()
    InitialSettingScreen(navController = navController)
}