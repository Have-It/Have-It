package com.habit.ui.screen.login.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.habit.R
import com.habit.ui.common.component.MediumRoundButton
import com.habit.ui.common.component.MediumSquareButton
import com.habit.ui.theme.HabitPurpleNormal
import com.habit.ui.theme.HabitTheme
import com.habit.ui.theme.KakaoYellow

@Composable
fun LoginButtonsComponent(
    onNormalLoginButtonClicked: () -> Unit,
    onKakaoLoginButtonClicked: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MediumRoundButton(
            text = stringResource(id = R.string.login_normal_login_button_text),
            color = HabitPurpleNormal,
            textStyle = HabitTheme.typography.mediumBoldTextWhite
        ) {
            //일반 로그인 처리
            onNormalLoginButtonClicked()
        }
        Spacer(modifier = Modifier.size(20.dp))
        Text(
            text = stringResource(id = R.string.login_or),
            style = HabitTheme.typography.mediumBodyPurpleNormal
        )
        Spacer(modifier = Modifier.size(20.dp))
        MediumSquareButton(
            text = stringResource(id = R.string.login_kakao_login_button_text),
            color = KakaoYellow,
            textStyle = HabitTheme.typography.mediumSquareButtonTextKakaoLabel,
            leftIcon = painterResource(id = R.drawable.icon_kakao),
            leftIconDescription = stringResource(id = R.string.login_kakao_login_button_text)
        ) {
            //카카오 로그인 처리
            onKakaoLoginButtonClicked()
        }
    }

}
