package com.habit.ui.screen.login

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.habit.R
import com.habit.ui.common.component.SquareBoxRoundedCorner
import com.habit.ui.navigation.InitialSettingNav
import com.habit.ui.navigation.LoginNav
import com.habit.ui.screen.login.component.LoginButtonsComponent
import com.habit.ui.screen.login.component.NormalLoginComponent
import com.habit.ui.theme.HabitPurpleExtraLight
import com.habit.ui.theme.HabitPurpleExtraLight2
import com.habit.ui.theme.HabitTheme
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient


private const val TAG = "LoginScreen"

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    var idText by remember { mutableStateOf("") }
    var directInputText by remember { mutableStateOf("") }
    var selectedDomainText by remember { mutableStateOf("") }
    var passWordText by remember { mutableStateOf("") }
    val context = LocalContext.current
    val email: MutableList<String> = mutableListOf<String>()
        .apply {
            add("gmail.com")
            add("naver.com")
            add("kakao.com")
            add("daum.net")
            add("hanmail.net")
            add(context.getString(R.string.login_domain_input_direct))
        }
    Log.d(TAG, "LoginScreen: 화면 리렌더링")

    LaunchedEffect(Unit) {
        selectedDomainText = email[0]
    }

    LaunchedEffect(uiState) {
        if (uiState == LoginUiState.LoginSuccess) {
            navController.navigate(InitialSettingNav.route) {
                popUpTo(LoginNav.route) {
                    inclusive = true
                }
            }
        }
        if(uiState == LoginUiState.LoginFail){
            Toast.makeText(context,"이메일 혹은 비밀번호가 틀렸습니다.", Toast.LENGTH_LONG).show()
            viewModel.resetLoginState()
        }
    }

    if(uiState == LoginUiState.NotLogined){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = HabitPurpleExtraLight)
                .padding(30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Image(
                contentScale = ContentScale.FillWidth,
                painter = painterResource(
                    R.drawable.image_logo_title
                ),
                contentDescription = "content description",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(50.dp, 0.dp)
            )
            SquareBoxRoundedCorner(
                backgroundColor = HabitPurpleExtraLight2,
                paddingHorizontal = 20.dp,
                paddingVertical = 30.dp,
                content = {
                    NormalLoginComponent(
                        email,
                        idText,
                        selectedDomainText,
                        { changedIdText ->
                            idText = changedIdText
                        },
                        { selectedText ->
                            selectedDomainText = selectedText
                        },
                        true,
                        directInputText,
                        { changedDirectInputText ->
                            directInputText = changedDirectInputText
                        },
                        passWordText,
                        { changedPassWordText ->
                            passWordText = changedPassWordText
                        }
                    )
                }
            )
            LoginButtonsComponent(
                onNormalLoginButtonClicked = {
                    Log.d(TAG, "LoginScreen: id: ${idText}")
                    var  domainTextToSend = ""
                    if (context.getString(R.string.login_domain_input_direct) == selectedDomainText) {
                        Log.d(TAG, "LoginScreen: domain: ${directInputText}")
                        domainTextToSend = directInputText
                    } else {
                        Log.d(TAG, "LoginScreen: domain: ${selectedDomainText}")
                        domainTextToSend = selectedDomainText
                    }
                    Log.d(TAG, "LoginScreen: pass: ${passWordText}")
                    if(idText != "" && domainTextToSend != "" && passWordText != ""){
                        viewModel.normalLogin("${idText}@${domainTextToSend}", passWordText)
                    }else{
                        Toast.makeText(context, context.getText(R.string.login_empty_warning), Toast.LENGTH_LONG).show()
                    }
                },
                onKakaoLoginButtonClicked = { login(navController, viewModel, context) }
            )
        }
    } else if(uiState == LoginUiState.Loading){
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(color = HabitPurpleExtraLight)
//                .padding(30.dp),
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.SpaceAround
//        ) {
//            Text(text = "로딩중", style = HabitTheme.typography.boldBodyGray)
//        }
    }



}


private fun login(navController: NavController, viewModel: LoginViewModel, context: Context) {
    // 카카오계정으로 로그인 공통 callback 구성
    val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            Log.e("카카오계정으로 로그인 실패", error.toString())
        } else if (token != null) {
            Log.d(TAG, "login: 카카오 로그인 성공 ${token.accessToken}")
            // jwt 토큰 발급 & 유저 정보
            viewModel.kakaoLogin(token.accessToken)
            // navController.navigate(InitialSettingNav.route)
        }
    }
    Log.d(TAG, "login: 로그인시작요")
    if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
        // 카카오톡 로그인
        UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
            // 로그인 실패 부분
            if (error != null) {
                Log.e(TAG, "로그인 실패 $error")
                // 사용자가 취소
                if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                    return@loginWithKakaoTalk
                }
                // 다른 오류, 이메일로 로그인 시도
                else {
                    UserApiClient.instance.loginWithKakaoAccount(
                        context,
                        callback = callback
                    ) // 카카오 이메일 로그인
                }
            }
            // 로그인 성공 부분
            else if (token != null) {
                Log.d(TAG, "login: 로그인성공이요 ${token.accessToken}")
                viewModel.kakaoLogin(token.accessToken)
                //    navController.navigate(InitialSettingNav.route)
            }
        }
    } else {
        // 카카오계정으로 로그인
        UserApiClient.instance.loginWithKakaoAccount(
            context,
            callback = callback
        )
    }
}
