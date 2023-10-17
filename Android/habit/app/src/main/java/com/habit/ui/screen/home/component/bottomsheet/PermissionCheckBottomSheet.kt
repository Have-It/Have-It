package com.habit.ui.screen.home.component.bottomsheet

import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.PermissionController

@Composable
fun PermissionCheckBottomSheet(
    permissions: Set<String>,
    permissionsLauncher: ManagedActivityResultLauncher<Set<String>, Set<String>>
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "수면, 걸음수 데이터를 얻기 위해 허용이 필요합니다!")
        Button(
            onClick = {
                permissionsLauncher.launch(permissions)
            }
        ) {
            Text(text = "접근 허용하기")
        }
        Spacer(modifier = Modifier.size(100.dp))
    }
}