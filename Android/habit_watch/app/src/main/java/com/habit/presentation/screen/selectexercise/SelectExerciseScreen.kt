package com.habit.presentation.screen.selectexercise

import android.content.ContentValues
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.wear.compose.foundation.lazy.AutoCenteringParams
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.itemsIndexed
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.material.ChipDefaults
import androidx.wear.compose.material.CompactChip
import androidx.wear.compose.material.PositionIndicator
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.Vignette
import androidx.wear.compose.material.VignettePosition
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.habit.R
import com.habit.data.ServiceState
import com.habit.data.model.HabitExerciseType
import com.habit.presentation.common.FontStyles
import com.habit.presentation.navigation.Screen
import com.habit.presentation.screen.ExerciseCapabilityState
import com.habit.presentation.screen.MainViewModel
import com.habit.presentation.screen.exercisesummary.SummaryState
import com.habit.presentation.screen.loading.LoadingScreen
import com.habit.presentation.screen.selectexercise.component.ExerciseChip
import com.habit.presentation.theme.HabitPurpleNormal
import java.time.ZonedDateTime


private const val TAG = "SelectExerciseScreen"

@Composable
fun SelectExerciseScreen(
    navController: NavHostController,
    mainViewModel: MainViewModel,
    viewModel: SelectExerciseViewModel
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val permissionState by viewModel.permissionState.collectAsState()
    val exerciseCapabilityState by mainViewModel.exerciseCapability.collectAsState()
    val summaryState by mainViewModel.summaryValueState.collectAsState()
    val listState = rememberScalingLazyListState()
    val now = ZonedDateTime.now()

    val exerciseList = listOf<HabitExerciseType>(
        HabitExerciseType.WALK,
        HabitExerciseType.GOLF,
        HabitExerciseType.BASKETBALL,
        HabitExerciseType.RUN,
        HabitExerciseType.HIKING,
        HabitExerciseType.TREADMILL,
        HabitExerciseType.MEDITATION,
        HabitExerciseType.MARTIAL_ARTS,
        HabitExerciseType.VOLLEYBALL,
        HabitExerciseType.BADMINTON,
        HabitExerciseType.SWIM,
        HabitExerciseType.SQUAT,
        HabitExerciseType.ROCK_CLIMBING,
        HabitExerciseType.BICYCLE,
        HabitExerciseType.SKIPPING_ROPE,
        HabitExerciseType.SOCCER,
        HabitExerciseType.DANCE,
        HabitExerciseType.TENNIS
    )

    /** Request permissions prior to launching exercise.**/
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { result ->
        if (result.all { it.value }) {
            Log.d(ContentValues.TAG, "All required permissions granted")
            viewModel.setPermissionState(PermissionState.PermissionAccepted)
        } else {
            viewModel.setPermissionState(PermissionState.PermissionNotAccepted)
        }
    }

    //서비스 상태가 연결된 상태일때 사용자에게 퍼미션 요청
    if (uiState.serviceState is ServiceState.Connected) {
        val requiredPermissions = uiState.requiredPermissions
        LaunchedEffect(requiredPermissions) {
            permissionLauncher.launch(requiredPermissions.toTypedArray())
        }
    }

    LaunchedEffect(Unit) {
        Log.d(TAG, "SelectExerciseScreen: reset!")
        mainViewModel.resetCapability()
        mainViewModel.resetSummary()
    }
    if(now.hour == 2 && now.minute>=50){
        Scaffold(
            vignette = { Vignette(vignettePosition = VignettePosition.TopAndBottom) },
            positionIndicator = {
                PositionIndicator(
                    scalingLazyListState = listState,
                )
            }
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    textAlign = TextAlign.Center,
                    text = stringResource(id = R.string.select_exercise_maintainance),
                    style = FontStyles.mediumBold
                )
            }
        }
    }else{
        if (
            permissionState == PermissionState.PermissionAccepted &&
            exerciseCapabilityState == ExerciseCapabilityState.Loading &&
            summaryState == SummaryState.Loading
        ) {
            Scaffold(
                vignette = { Vignette(vignettePosition = VignettePosition.TopAndBottom) },
                positionIndicator = {
                    PositionIndicator(
                        scalingLazyListState = listState,
                    )
                }
            ) {
                ScalingLazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    autoCentering = AutoCenteringParams(itemIndex = 0),
                    state = listState
                ) {
                    item {
                        Text(
                            text = stringResource(id = R.string.select_exercise_title),
                            style = FontStyles.mediumBold
                        )
                    }
                    itemsIndexed(exerciseList) { index, item ->
                        ExerciseChip(exerciseType = item) {
                            Log.d(TAG, "SelectExerciseScreen: 클릭됨")
                            mainViewModel.selectExcercise(item)
                            navController.navigate(Screen.StartExerciseNav.route)
                        }
                    }
                }
            }
        } else if (permissionState == PermissionState.PermissionNotAccepted) {
            Scaffold(
                vignette = { Vignette(vignettePosition = VignettePosition.TopAndBottom) },
                positionIndicator = {
                    PositionIndicator(
                        scalingLazyListState = listState,
                    )
                }
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = stringResource(id = R.string.select_exercise_need_permission),
                        style = FontStyles.mediumBold
                    )
                    CompactChip(
                        colors = ChipDefaults.chipColors(backgroundColor = HabitPurpleNormal),
                        label = {
                            Text(
                                text = stringResource(id = R.string.select_exercise_go_to_permission),
                                style = FontStyles.smallBold.copy(color = Color.White)
                            )
                        },
                        onClick = {
                            val requiredPermissions = uiState.requiredPermissions
                            permissionLauncher.launch(requiredPermissions.toTypedArray())

                        },
                    )
                }
            }
        } else {
            LoadingScreen()
        }
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
    val mainViewModel: MainViewModel = viewModel()
    val viewModel: SelectExerciseViewModel = viewModel()
    val navController = rememberSwipeDismissableNavController()
    SelectExerciseScreen(navController, mainViewModel, viewModel)
}