package com.habit.ui.screen.HealthConnectSetting

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.health.connect.client.PermissionController
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavHostController
import com.habit.ui.navigation.BottomNav
import com.habit.ui.navigation.HealthConnectSettingNav
import com.habit.ui.screen.MainViewModel

private const val TAG = "HealthConnectSettingScr"

@Composable
fun HealthConnectSettingScreen(
    navController: NavHostController,
    viewModel: HealthConnectSettingViewModel = hiltViewModel(),
    mainViewModel: MainViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current

    val permissionsLauncher =
        rememberLauncherForActivityResult(PermissionController.createRequestPermissionResultContract()) {
            //퍼미션 한번 더 확인하는 처리
        }
    val uiState by viewModel.healthConnectState.collectAsState()

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                viewModel.getHealthConnectState()
            }
        }

        // Add the observer to the lifecycle
        lifecycleOwner.lifecycle.addObserver(observer)

        // When the effect leaves the Composition, remove the observer
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    LaunchedEffect(uiState) {
        if (uiState == HealthConnectState.HealthConnectReady) {

            navController.navigate(BottomNav.Home.route) {
                popUpTo(HealthConnectSettingNav.route) {
                    inclusive = true
                }
            }

        }
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when (uiState) {
            HealthConnectState.HealthConnectNotSupport -> {
                Text(text = "지원하지 않는 기기입니다.")
            }

            HealthConnectState.HealthConnectNotInstalled -> {
                Text(text = "수면 데이터 관리를 위해 헬스 커넥트를 설치해주세요!")
                Button(onClick = {
                    context.startActivity(
                        Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.google.android.apps.healthdata"))
                    )
                }) {
                    Text(text = "설치하러 가기!")
                }
            }

            else -> {

            }
        }
    }

}