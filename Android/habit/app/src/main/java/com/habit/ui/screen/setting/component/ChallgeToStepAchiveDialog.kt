package com.habit.ui.screen.setting.component

import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.health.connect.client.PermissionController
import com.habit.ui.common.component.CustomDialog
import com.habit.ui.common.component.MediumRoundButton
import com.habit.ui.screen.setting.StepPermissionState
import com.habit.ui.theme.HabitPurpleNormal
import com.habit.ui.theme.HabitTheme
import com.habit.R

@Composable
fun ChallengeToStepAchiveDialog(
    onDismissRequest: () -> Unit,
    permissions: Set<String>,
    permissionState: StepPermissionState,
    stepCount: Int,
    refresh: () -> Unit,
    onChallengeButtonClicked: () -> Unit
) {
    if (permissionState == StepPermissionState.StepPermissionAccepted) {
        CustomDialog(content = {
            ChallengeToWalkAchive(stepCount = stepCount, onClick = onChallengeButtonClicked)
        }, onDismissRequest = onDismissRequest)
    } else {
        CustomDialog(content = {
            needPermissionDialogContent(permissions = permissions, refresh = refresh)
        }, onDismissRequest = onDismissRequest)
    }
}

@Composable
fun ChallengeToWalkAchive(
    stepCount: Int,
    onClick: () -> Unit
){
    Column(
        modifier = Modifier
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "걷기왕에 도전!",
            style = HabitTheme.typography.headTextPurpleNormal,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.size(30.dp))
        Image(
            modifier = Modifier.size(150.dp),
            contentScale = ContentScale.Fit,
            painter = painterResource(id = R.drawable.image_trophy),
            contentDescription = null
        )
        Spacer(modifier = Modifier.size(30.dp))
        Text(
            text = "도전을 위해 아래 버튼을 눌러 걷기데이터를 저장하세요!\n다음날 새벽3시 이후로 반영됩니다.",
            style = HabitTheme.typography.miniBoldBody,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.size(10.dp))
        Text(text = "현재 걸음 수: ${stepCount}", style = HabitTheme.typography.mediumBoldTextBlack)
        Spacer(modifier = Modifier.size(10.dp))
        MediumRoundButton(
            text = "현재 걸음수로 도전하기",
            color = HabitPurpleNormal,
            textStyle = HabitTheme.typography.mediumBoldTextWhite.copy(color = Color.White)
        ) {
            onClick()
        }
    }
}

@Composable
fun needPermissionDialogContent(
    permissions: Set<String>,
    refresh: () -> Unit
) {
    val permissionsLauncher: ManagedActivityResultLauncher<Set<String>, Set<String>> =
        rememberLauncherForActivityResult(PermissionController.createRequestPermissionResultContract()) {
            refresh()// 화면 새로고침
        }
    Column(
        modifier = Modifier.padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "걸음수 읽기 권한이 필요합니다!",
            style = HabitTheme.typography.headTextPurpleNormal,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.size(10.dp))
        MediumRoundButton(
            text = "권한 허용하기",
            color = HabitPurpleNormal,
            textStyle = HabitTheme.typography.miniBoldBody.copy(color = Color.White)
        ) {
            permissionsLauncher.launch(permissions)
        }
    }
}