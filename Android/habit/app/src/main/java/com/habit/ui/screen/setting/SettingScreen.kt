package com.habit.ui.screen.setting

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.habit.R
import com.habit.ui.navigation.BottomNav
import com.habit.ui.navigation.LoginNav
import com.habit.ui.screen.RefreshTokenState
import com.habit.ui.screen.setting.component.ChallengeToStepAchiveDialog
import com.habit.ui.screen.setting.component.SettingHeader
import com.habit.ui.screen.setting.component.SettingScreenMenu
import java.time.ZonedDateTime

private const val TAG = "SettingScreen"
@Composable
fun SettingScreen(
    navController: NavHostController,
    viewModel: SettingViewModel = hiltViewModel()
) {
    var showDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val refreshTokenState by viewModel.refreshTokenState.collectAsState()
    val uiState by viewModel.uiState.collectAsState()
    val memberSettingPageInfoUiState by viewModel.memberSettingPageInfoUiState.collectAsState()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val memberSettingPageInfo by viewModel.memberSettingPageInfo.collectAsState()
    val statUiState by viewModel.statUiState.collectAsState()
    val stat by viewModel.stat.collectAsState()
    val permissionState by viewModel.stepPermissionState.collectAsState()
    val stepCount by viewModel.stepCount.collectAsState()
    var imageId by remember { mutableStateOf(0) }

    val currentRoute = navBackStackEntry?.destination?.route

    LaunchedEffect(memberSettingPageInfo){
        Log.d(TAG, "SettingScreen: 변경 ${memberSettingPageInfo.settingPageProfileImage}")
        if(memberSettingPageInfoUiState == MemberSettingPageInfoUiState.MemberSettingInfoLoaded){
            imageId = imageId+1
        }
    }
    Log.d(TAG, "SettingScreen: ${memberSettingPageInfo.settingPageProfileImage}")

    LaunchedEffect(uiState) {
        if (uiState == SettingUiState.Logout) {
            navController.navigate(LoginNav.route) {
                popUpTo(BottomNav.Setting.route) {
                    inclusive = true
                }
            }
//            navController.popBackStack(currentRoute.toString(), true)
//            navController.navigate(GoLoginNav.route)
        }
    }

    LaunchedEffect(permissionState){
        if(permissionState == StepPermissionState.StepPermissionAccepted){
            viewModel.getStepCount()
        }
    }

    LaunchedEffect(refreshTokenState){
        if(refreshTokenState == RefreshTokenState.NeedReLogin){
            Log.d(TAG, "SettingScreen: 리프레시 토큰 만료")

            navController.navigate(LoginNav.route) {
                popUpTo(BottomNav.Setting.route) {
                    inclusive = true
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.getStat()
        viewModel.checkStepPermission()
        viewModel.getMemberSettingPageInfo()
    }

    LaunchedEffect(memberSettingPageInfoUiState){
        if(memberSettingPageInfoUiState == MemberSettingPageInfoUiState.MemberHomeInfoErr){
            Toast.makeText(context, context.getString(R.string.setting_member_info_err), Toast.LENGTH_LONG).show()
        }
    }
    LaunchedEffect(statUiState){
        if(statUiState == StatUiState.StatErr){
            Toast.makeText(context, context.getString(R.string.setting_stat_err), Toast.LENGTH_LONG).show()
        }
    }

    if (
        memberSettingPageInfoUiState != MemberSettingPageInfoUiState.MemberSettingInfoLoading
        && statUiState != StatUiState.StatLoading
        && permissionState != StepPermissionState.StepPermissionStateLoading
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(36.dp, 30.dp)
        ) {
            SettingHeader(memberSettingPageInfo, stat, imageId)
            Spacer(modifier = Modifier.size(40.dp))
            SettingScreenMenu(menuText = stringResource(id = R.string.setting_challenge_to_step_achive)) {
                showDialog = true
            }
            Spacer(modifier = Modifier.size(24.dp))
            SettingScreenMenu(menuText = stringResource(id = R.string.setting_modifiy_mission)) {

            }
            Spacer(modifier = Modifier.size(24.dp))
            SettingScreenMenu(menuText = stringResource(id = R.string.setting_logout)) {
                viewModel.logout()
            }
            Spacer(modifier = Modifier.size(24.dp))
            SettingScreenMenu(menuText = stringResource(id = R.string.setting_withdraw)) {

            }
        }
        if(showDialog){
            ChallengeToStepAchiveDialog(
                onDismissRequest = { showDialog = false },
                permissions = viewModel.permissions,
                permissionState = permissionState,
                refresh = {viewModel.checkStepPermission()},
                stepCount = stepCount,
                onChallengeButtonClicked = {
                    val now = ZonedDateTime.now()
                    if(now.hour == 2 && now.minute>=50){
                        Toast.makeText(context, "2시 50분 ~ 3시 앱 점검시간입니다", Toast.LENGTH_LONG).show()
                    }else{
                        viewModel.putStepCount(stepCount)
                        showDialog = false
                    }
                }
            )
        }
    }
}

@Composable
@Preview
fun PrevSettingScreen() {
    val navController = rememberNavController()
    SettingScreen(navController = navController)
}