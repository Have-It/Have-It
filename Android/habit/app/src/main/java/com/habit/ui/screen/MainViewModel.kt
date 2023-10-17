package com.habit.ui.screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.habit.domain.model.mission.MissionDto
import com.habit.domain.model.user.MemberInfoDto
import com.habit.domain.useCase.main.MainUseCases
import com.habit.ui.screen.initialsetting.InitialSettingUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "MainViewModel"
//여러 페이지에서 공통적으로 필요한 데이터를 관리한다
@HiltViewModel
class MainViewModel @Inject constructor(
    private val useCases: MainUseCases
) : ViewModel() {
    private val _initialSettingUiState = MutableStateFlow<InitialSettingUiState>(InitialSettingUiState.Loading)
    val initialSettingUiState: StateFlow<InitialSettingUiState> = _initialSettingUiState.asStateFlow()

    //로그인 한 유저의 정보
    private val _memberInfo: MutableStateFlow<MemberInfoDto> = MutableStateFlow(MemberInfoDto())
    val memberInfo: StateFlow<MemberInfoDto> = _memberInfo

    //미션 데이터
    private val _missionInfo: MutableStateFlow<MissionDto> = MutableStateFlow(MissionDto(-1, -1, -1, -1, "", ""))
    val missionInfo: StateFlow<MissionDto> = _missionInfo

    //유저 이미지, 닉네임,

    fun checkMissionUserId(){
        viewModelScope.launch {
            val memberInfo = useCases.getMemberInfoUseCase.invoke()
            val missionInfo = useCases.getMissionUseCase.invoke()
            _memberInfo.emit(memberInfo)
            _missionInfo.emit(missionInfo)
            if((memberInfo.memberId == missionInfo.userId)&& missionInfo.userId != -1){
                updateUiState(InitialSettingUiState.MissionExist)
            }else{
                updateUiState(InitialSettingUiState.MissionNotExist)
            }
        }
    }

    fun updateUiState(state: InitialSettingUiState){
        _initialSettingUiState.value = state
    }

    fun getMemberInfo(){
        viewModelScope.launch {
            _memberInfo.emit(useCases.getMemberInfoUseCase.invoke())
        }
    }

    fun getMissionInfo(){
        viewModelScope.launch {
            _missionInfo.emit(useCases.getMissionUseCase.invoke())
        }
    }

}