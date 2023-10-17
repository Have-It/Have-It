package com.habit.ui.screen.home

import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.health.connect.client.PermissionController
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.habit.ui.screen.MainViewModel
import com.habit.ui.screen.home.component.body.BodyComponent
import com.habit.ui.screen.home.component.bottomsheet.CalorieBottomSheet
import com.habit.ui.screen.home.component.bottomsheet.FreeBottomSheet
import com.habit.ui.screen.home.component.bottomsheet.PermissionCheckBottomSheet
import com.habit.ui.screen.home.component.bottomsheet.SleepBottomSheet
import com.habit.ui.screen.home.component.header.HeaderComponent
import com.habit.ui.theme.HabitPurpleExtraLight
import com.habit.R
import com.habit.ui.navigation.BottomNav
import com.habit.ui.navigation.LoginNav
import com.habit.ui.screen.RefreshTokenState
import com.habit.ui.theme.HabitPurpleExtraLight3
import com.habit.ui.theme.HabitTheme
import kotlinx.coroutines.flow.collect

private const val TAG = "HomeScreen"

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel(),
    mainViewModel: MainViewModel = hiltViewModel()
) {
    val refreshTokenState by viewModel.refreshTokenState.collectAsState()

    val context = LocalContext.current
    val sleepPermissionState by viewModel.sleepPermissionState.collectAsState()
    val missionUiState by viewModel.homeMissionUiState.collectAsState()
    val memberHomeInfoUiState by viewModel.memberHomeInfoUiState.collectAsState()

    val totalCalorie by viewModel.totalCalorie.collectAsState()
    val exerciseList by viewModel.exerciseList.collectAsState()
    val sleepSessionData by viewModel.sleepSessionData.collectAsState()
    val missionData by mainViewModel.missionInfo.collectAsState()
    val homeData by viewModel.homeData.collectAsState()

    val memberHomeInfo by viewModel.memberHomeInfoDto.collectAsState()

    val missionSuccessData by viewModel.missionSuccessData.collectAsState()

    val stepData by viewModel.stepData.collectAsState()

    val permissionsLauncher : ManagedActivityResultLauncher<Set<String>, Set<String>> =
        rememberLauncherForActivityResult(PermissionController.createRequestPermissionResultContract()) {
            viewModel.checkPermission()// 화면 새로고침
        }

    val calorieSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    val sleepSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    val freeSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    var showCalorieBottomSheet by remember { mutableStateOf(false) }
    var showSleepBottomSheet by remember { mutableStateOf(false) }
    var showFreeBottomSheet by remember { mutableStateOf(false) }

    LaunchedEffect(refreshTokenState) {
        Log.d(TAG, "SettingScreen: 리프레시 토큰 만료")
        if (refreshTokenState == RefreshTokenState.NeedReLogin) {
            navController.navigate(LoginNav.route) {
                popUpTo(BottomNav.Home.route) {
                    inclusive = true
                }
            }
        }

    }

    LaunchedEffect(Unit) {
        viewModel.checkPermission()
        viewModel.getMemberHomeInfo()
        mainViewModel.getMissionInfo()
    }

    LaunchedEffect(sleepPermissionState) {
        Log.d(TAG, "HomeScreen: ${sleepPermissionState}")
        if (sleepPermissionState != SleepPermissionState.PermissionStateLoading) {
            viewModel.getHomeData()
            viewModel.getStepData()
        }
    }

    Log.d(TAG, "HomeScreen: missionUiState ${missionUiState == MissionUiState.MissionLoading}")
    if (missionUiState != MissionUiState.MissionLoading && memberHomeInfoUiState != MemberHomeInfoState.MemberHomeInfoLoading) {
        if (missionUiState == MissionUiState.MissionErr || memberHomeInfoUiState == MemberHomeInfoState.MemberHomeInfoErr) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(id = R.string.home_error),
                    style = HabitTheme.typography.headTextPurpleNormal,
                    textAlign = TextAlign.Center
                )
            }
        } else {
            Column(modifier = Modifier.background(color = HabitPurpleExtraLight)) {

                Box(modifier = Modifier.weight(2f)) {
                    HeaderComponent(
                        sleepPermissionState,
                        memberHomeInfo,
                        homeData,
                        stepData,
                        viewModel.permissions,
                        permissionsLauncher
                    )
                }
                Box(modifier = Modifier.weight(3f)) {
                    BodyComponent(
                        missionSuccessData,
                        missionData,
                        totalCalorie,
                        sleepPermissionState,
                        sleepSessionData.totalSleepTime,
                        onCalorieMissionCardClicked = {
                            showCalorieBottomSheet = true
                        },
                        onSleepMissionCardClicked = {
                            showSleepBottomSheet = true
                        },
                        onFreeMissionCardClicked = {
                            showFreeBottomSheet = true
                        },
                    )
                }
            }
            if (showCalorieBottomSheet) {
                ModalBottomSheet(
                    onDismissRequest = {
                        showCalorieBottomSheet = false
                    },
                    sheetState = calorieSheetState
                ) {
                    CalorieBottomSheet(
                        missionSuccessData.calorieMissionSuccessState,
                        totalCalorie,
                        exerciseList,
                        missionData
                    ) {
                        viewModel.postExerciseMissionSuccess()
                        showCalorieBottomSheet = false
                    }
                }
            }
            if (showSleepBottomSheet) {
                ModalBottomSheet(
                    onDismissRequest = {
                        showSleepBottomSheet = false
                    },
                    sheetState = sleepSheetState
                ) {
                    if (sleepPermissionState == SleepPermissionState.PermissionAccepted) {
                        SleepBottomSheet(
                            missionSuccessData.sleepMissionSuccessState,
                            sleepSessionData,
                            missionData
                        ) {
                            viewModel.postSleepMissionSuccess()
                            showSleepBottomSheet = false
                        }
                    } else if (sleepPermissionState == SleepPermissionState.PermissionNotAccepted) {
                        PermissionCheckBottomSheet(
                            viewModel.permissions,
                            permissionsLauncher
                        )
                    }
                }
            }
            if (showFreeBottomSheet) {
                ModalBottomSheet(
                    onDismissRequest = {
                        showFreeBottomSheet = false
                    },
                    sheetState = freeSheetState
                ) {
                    FreeBottomSheet(
                        missionSuccessData.freeMissionSuccessState,
                        missionData
                    ) {
                        viewModel.postFreeMissionSuccess()
                        showFreeBottomSheet = false
                    }
                }
            }
        }


    }


}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
@Preview
fun PrevHomeScreen() {
    val navController = rememberNavController()
    HomeScreen(navController = navController)
}