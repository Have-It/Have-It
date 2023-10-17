package com.habit.ui.screen.setting

import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.records.SleepSessionRecord
import androidx.health.connect.client.records.StepsRecord
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.habit.domain.model.user.MemberSettingPageInfoDto
import com.habit.domain.model.user.StatDto
import com.habit.domain.onError
import com.habit.domain.onSuccess
import com.habit.domain.useCase.setting.SettingUseCases
import com.habit.ui.screen.RefreshTokenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.ZonedDateTime
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val useCases: SettingUseCases,
    private val healthConnectClient: HealthConnectClient,
    ) : ViewModel() {
    val permissions = setOf(
        HealthPermission.getReadPermission(StepsRecord::class)
    )

    private val _refreshTokenState = MutableStateFlow<RefreshTokenState>(RefreshTokenState.NotNeedReLogin)
    var refreshTokenState: StateFlow<RefreshTokenState> = _refreshTokenState.asStateFlow()

    private val _uiState = MutableStateFlow<SettingUiState>(SettingUiState.NotLogouted)
    val uiState: StateFlow<SettingUiState> = _uiState.asStateFlow()

    private val _stepPermissionState = MutableStateFlow<StepPermissionState>(StepPermissionState.StepPermissionStateLoading)
    var stepPermissionState: StateFlow<StepPermissionState> = _stepPermissionState.asStateFlow()

    private val _memberSettingPageInfoUiState =
        MutableStateFlow<MemberSettingPageInfoUiState>(MemberSettingPageInfoUiState.MemberSettingInfoLoading)
    val memberSettingPageInfoUiState: StateFlow<MemberSettingPageInfoUiState> =
        _memberSettingPageInfoUiState.asStateFlow()

    private val _statUiState = MutableStateFlow<StatUiState>(StatUiState.StatLoading)
    val statUiState: StateFlow<StatUiState> = _statUiState.asStateFlow()

    private val _stepCount = MutableStateFlow<Int>(0)
    val stepCount: StateFlow<Int> = _stepCount.asStateFlow()

    //멤버 세팅 화면 정보(닉네임, 캐릭터 상반신 이미지)
    private val _memberSettingPageInfo: MutableStateFlow<MemberSettingPageInfoDto> =
        MutableStateFlow(
            MemberSettingPageInfoDto("", "")
        )
    val memberSettingPageInfo: StateFlow<MemberSettingPageInfoDto> =
        _memberSettingPageInfo.asStateFlow()

    //캐릭터 몸무게, 다크써클
    private val _stat: MutableStateFlow<StatDto> = MutableStateFlow(StatDto(-1, -1))
    val stat: StateFlow<StatDto> = _stat.asStateFlow()

    fun getMemberSettingPageInfo() {
        viewModelScope.launch {
            val memberSettingPageInfoResult = useCases.getMemberSettingPageInfoUseCase.invoke()
            memberSettingPageInfoResult.onSuccess {
                _memberSettingPageInfo.emit(it)
                _memberSettingPageInfoUiState.emit(MemberSettingPageInfoUiState.MemberSettingInfoLoaded)
            }
            memberSettingPageInfoResult.onError {
                when (it.message) {
                    "required_re_login"->{
                        useCases.deleteAccessTokenUseCase.invoke()
                        _refreshTokenState.emit(RefreshTokenState.NeedReLogin)
                    }
                    else -> {
                        _memberSettingPageInfoUiState.emit(MemberSettingPageInfoUiState.MemberHomeInfoErr)
                    }
                }
            }
        }
    }

    fun getStat() {
        viewModelScope.launch {
            val statResult = useCases.getStatUseCase.invoke()
            statResult.onSuccess {
                _stat.emit(it)
                _statUiState.emit(StatUiState.StatLoaded)
            }
            statResult.onError {
                when (it.message) {
                    "required_re_login"->{
                        useCases.deleteAccessTokenUseCase.invoke()
                        _refreshTokenState.emit(RefreshTokenState.NeedReLogin)
                    }
                    else -> {
                        _statUiState.emit(StatUiState.StatErr)
                    }
                }

            }
        }
    }

    fun checkStepPermission(){
        viewModelScope.launch {
            val permissionState = healthConnectClient.permissionController.getGrantedPermissions()
                .containsAll(permissions)
            if(permissionState){
                _stepPermissionState.emit(StepPermissionState.StepPermissionAccepted)
            }else{
                _stepPermissionState.emit(StepPermissionState.StepPermissionNotAccepted)
            }
        }
    }

    fun getStepCount(){
        viewModelScope.launch {
            val permissionState = healthConnectClient.permissionController.getGrantedPermissions()
                .containsAll(permissions)
            if(permissionState){
                val res = useCases.getStepDataUseCase.invoke(ZonedDateTime.now())
                if(res > Int.MAX_VALUE){
                    _stepCount.emit(Int.MAX_VALUE)
                }else{
                    _stepCount.emit(res.toInt())
                }
                _stepPermissionState.emit(StepPermissionState.StepPermissionAccepted)
            }else{
                _stepPermissionState.emit(StepPermissionState.StepPermissionNotAccepted)
            }
        }
    }

    fun putStepCount(step: Int){
        viewModelScope.launch {
            useCases.putStepDataUseCase.invoke(step)
        }
    }

    fun logout() {
        viewModelScope.launch {
            useCases.logoutUseCase.invoke()
            useCases.deleteAccessTokenUseCase.invoke()
//            useCases.deleteNormalLoginInfoUseCase.invoke()
//            useCases.deleteKakaoTokenUseCase.invoke()
            _uiState.value = SettingUiState.Logout
        }
    }
}