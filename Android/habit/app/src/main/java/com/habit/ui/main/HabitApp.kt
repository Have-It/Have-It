package com.habit.ui.main

import android.Manifest
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.habit.ui.navigation.HomeScreenNavGraph
import com.habit.ui.navigation.MainBottomNavigationBar
import com.habit.ui.theme.HabitTheme

@OptIn(ExperimentalAnimationApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HabitApp() {
    HabitTheme {
        val homeNavController = rememberAnimatedNavController()
        Scaffold(
            bottomBar = { MainBottomNavigationBar(navController = homeNavController) }
        ) {
            HomeScreenNavGraph(
                navController = homeNavController,
                innerPadding = it
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun FeatureThatRequiresCameraPermission() {

    // notification
    val notification = rememberPermissionState(
        Manifest.permission.POST_NOTIFICATIONS
    )

    if (notification.status.isGranted) {
//        Text("Camera permission Granted")
    } else {
        SideEffect {
            notification.launchPermissionRequest()
        }
    }
}