package com.habit.ui.screen.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.habit.domain.onError
import com.habit.domain.onSuccess
import com.habit.domain.useCase.login.LoginUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "LoginViewModel"

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val useCases: LoginUseCases
) : ViewModel() {
    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Loading)
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    init {
        //자동 로그인 처리
        viewModelScope.launch {
            val accessToken = useCases.getAccessTokenUseCase.invoke()
            if(accessToken == ""){ // 엑세스 토큰이 없는 경우, 최초로그인 혹은 로그아웃한 경우다
                _uiState.value = LoginUiState.NotLogined
            }else{ //엑세스 토큰이 있는경우, 로그아웃하지 않은 상태, 즉 자동로그인을 해야하는 상황이다.
                Log.d(TAG, "엑세스토큰 있는데요")
                _uiState.value = LoginUiState.LoginSuccess
            }
//            val normalLoginInfo = useCases.getNormalLoginInfoUseCase.invoke()
//            Log.d(TAG, "normal logdininfo: ${normalLoginInfo.email} ${normalLoginInfo.pass}")
//            val kakaoToken = useCases.getKakaoTokenUseCase.invoke()
//            Log.d(TAG, "kakaotoekn: ${kakaoToken}")
//
//            if(normalLoginInfo.email !="" && normalLoginInfo.pass != ""){
//                val normalLoginResult = useCases.normalLoginUseCase.invoke(normalLoginInfo.email, normalLoginInfo.pass)
//                if(normalLoginResult.memberId != -1){
//                    useCases.putMemberInfoUseCase.invoke(normalLoginResult)
//                    _uiState.value = LoginUiState.LoginSuccess
//                }else{
//                    Log.d(TAG, "로그인 토큰 만료")
//                    _uiState.value = LoginUiState.NotLogined
//                }
//            }else if(kakaoToken != ""){
//                val kakaoTokenLoginResult = useCases.kakaoLoginUseCase.invoke(kakaoToken)
//                if(kakaoTokenLoginResult.memberId != -1){
//                    useCases.putMemberInfoUseCase.invoke(kakaoTokenLoginResult)
//                    _uiState.value = LoginUiState.LoginSuccess
//                }else{
//                    Log.d(TAG, "카카오 로그인 만료")
//                    _uiState.value = LoginUiState.NotLogined
//                }
//            }else{
//                //그냥 최초 로그인
//                _uiState.value = LoginUiState.NotLogined
//            }

        }

    }


    fun kakaoLogin(kakaoToken: String){
        Log.d(TAG, "kakaoLogin: 보낸다 ${kakaoToken}")
        viewModelScope.launch {
            val res = useCases.kakaoLoginUseCase.invoke(kakaoToken)
            res.onSuccess {
                useCases.putAccessTokenUseCase.invoke(it.accessToken)
                useCases.putMemberInfoUseCase(it)
                _uiState.value = LoginUiState.LoginSuccess
                //useCases.putKakaoTokenUseCase.invoke(kakaoToken)
            }
            res.onError {
                _uiState.value = LoginUiState.LoginFail
            }
        }
    }

    fun normalLogin(email: String, password: String){
        viewModelScope.launch {
            val res = useCases.normalLoginUseCase.invoke(email, password)
//            res.onSuccess {
//                Log.d(TAG, "normalLogin success: ${it.accessToken}")
//            }
//            res.onError {
//                Log.d(TAG, "normalLogin err: ${it}")
//            }
            res.onSuccess {
                useCases.putAccessTokenUseCase.invoke(it.accessToken)
                useCases.putMemberInfoUseCase.invoke(it)
                _uiState.value = LoginUiState.LoginSuccess
                //  useCases.putNormalLoginInfoUseCase(email, password)
            }
            res.onError {
                _uiState.value = LoginUiState.LoginFail
            }
        }
    }

    fun resetLoginState(){
        viewModelScope.launch {
            _uiState.emit(LoginUiState.NotLogined)
        }
    }
}