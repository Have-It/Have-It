package com.habit.presentation.screen.exercisesummary

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.wear.compose.foundation.lazy.AutoCenteringParams
import androidx.wear.compose.material.ChipDefaults
import androidx.wear.compose.material.CompactChip
import androidx.wear.compose.material.Text
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.google.android.horologist.compose.layout.ScalingLazyColumn
import com.habit.R
import com.habit.app.ConnectState
import com.habit.app.DataLayerViewModel
import com.habit.data.model.ExerciseInfoType
import com.habit.presentation.common.FontStyles
import com.habit.presentation.navigation.Screen
import com.habit.presentation.screen.MainViewModel
import com.habit.presentation.screen.exercisesummary.component.ExerciseSummaryInfoBox
import com.habit.presentation.theme.HabitPurpleNormal
import com.habit.util.formatCalories
import com.habit.util.formatElapsedTime
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import com.habit.data.model.ExerciseResultDto
import com.habit.presentation.screen.loading.LoadingScreen
import com.habit.util.formatCalorieDoubleToInt
import java.time.ZonedDateTime

@Composable
fun ExerciseSummaryScreen(
    navController: NavHostController,
    mainViewModel: MainViewModel,
    dataLayerViewModel: DataLayerViewModel
) {
    val viewModel = hiltViewModel<ExerciseSummaryViewMode>()
    val summaryValue by mainViewModel.summaryValue.collectAsStateWithLifecycle()
    val summaryState by mainViewModel.summaryValueState.collectAsStateWithLifecycle()
    val selectedExercise by mainViewModel.selectedExcercise.collectAsState()
    val connectState by dataLayerViewModel.connectState.collectAsStateWithLifecycle()
    val listState = rememberScalingLazyListState()
    val now = remember { mutableStateOf(ZonedDateTime.now()) }

    LaunchedEffect(Unit) {
        dataLayerViewModel.checkConnected()
    }

    if (summaryState == SummaryState.SummaryValueLoaded && connectState != ConnectState.Loading) {
        ScalingLazyColumn(
            modifier = Modifier.fillMaxSize(),
            autoCentering = AutoCenteringParams(itemIndex = 0),
            state = listState
        ) {
            item {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(0.dp, 20.dp, 0.dp, 0.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.exercise_summary_title),
                        style = FontStyles.mediumNormal
                    )
                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = selectedExercise.excerciseName, style = FontStyles.mediumNormal)
                        Image(
                            painter = painterResource(id = selectedExercise.icon),
                            contentDescription = selectedExercise.excerciseName
                        )
                    }
                    ExerciseSummaryInfoBox(
                        exerciseInfoType = ExerciseInfoType.CALORIE,
                        value = formatCalories(summaryValue.totalCalories)
                    )
                    ExerciseSummaryInfoBox(
                        exerciseInfoType = ExerciseInfoType.TIME,
                        value = formatElapsedTime(summaryValue.elapsedTime, includeSeconds = true)
                    )
                    if(now.value.hour == 2 && now.value.minute>=50){
                        Text(
                            text = stringResource(id = R.string.exercise_summary_maintainance),
                            style = FontStyles.mediumNormal,
                            textAlign = TextAlign.Center
                        )
                        CompactChip(
                            colors = ChipDefaults.chipColors(backgroundColor = HabitPurpleNormal),
                            label = {
                                Text(
                                    text = stringResource(id = R.string.exercise_summary_refresh),
                                    style = FontStyles.smallBold.copy(color = Color.White)
                                )
                            },
                            onClick = {
                                now.value = ZonedDateTime.now()
                            },
                        )
                    }else{
                        if (connectState == ConnectState.Connected) {
                            CompactChip(
                                colors = ChipDefaults.chipColors(backgroundColor = HabitPurpleNormal),
                                label = {
                                    Text(
                                        text = stringResource(id = R.string.exercise_summary_save_button_text),
                                        style = FontStyles.smallBold.copy(color = Color.White)
                                    )
                                },
                                onClick = {
                                    dataLayerViewModel.sendData(
                                        ExerciseResultDto(
                                            selectedExercise.exerciseTypeString,
                                            formatCalorieDoubleToInt(summaryValue.totalCalories)
                                        )
                                    )
                                    mainViewModel.resetSummary()
                                    navController.navigate(Screen.SelectExerciseNav.route) {
                                        popUpTo(Screen.ExerciseSummaryNav.route) {
                                            inclusive = true
                                        }
                                    }
                                },
                            )
                        } else if (connectState == ConnectState.NotConnected) {
                            Text(
                                text = stringResource(id = R.string.exercise_summary_need_connect),
                                style = FontStyles.mediumNormal,
                                textAlign = TextAlign.Center
                            )
                            CompactChip(
                                colors = ChipDefaults.chipColors(backgroundColor = HabitPurpleNormal),
                                label = {
                                    Text(
                                        text = stringResource(id = R.string.exercise_summary_refresh),
                                        style = FontStyles.smallBold.copy(color = Color.White)
                                    )
                                },
                                onClick = {
                                    dataLayerViewModel.checkConnected()
                                },
                            )
                        }
                    }


                }
            }
        }

    } else {
        LoadingScreen()
    }

}

@Preview(
    device = "id:wearos_large_round",
    backgroundColor = 4278190080,
    showBackground = true,
    group = "Devices - Large Round",
    showSystemUi = true
)
@Composable
fun PrevSelectExerciseScreen() {
    val dataLayerViewModel: DataLayerViewModel = viewModel()
    val mainViewModel: MainViewModel = viewModel()
    val navController = rememberSwipeDismissableNavController()
    ExerciseSummaryScreen(navController, mainViewModel, dataLayerViewModel)
}