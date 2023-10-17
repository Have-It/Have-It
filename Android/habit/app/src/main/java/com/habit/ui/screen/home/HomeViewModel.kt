package com.habit.ui.screen.home

import android.util.Log
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.records.SleepSessionRecord
import androidx.health.connect.client.records.StepsRecord
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.habit.domain.model.home.HomeDataDto
import com.habit.domain.model.home.MemberHomeInfoDto
import com.habit.domain.model.mission.ExerciseDto
import com.habit.domain.model.mission.MissionDto
import com.habit.domain.model.mission.SleepSessionDto
import com.habit.domain.onError
import com.habit.domain.onSuccess
import com.habit.domain.useCase.home.HomeUseCases
import com.habit.ui.common.model.MissionStateType
import com.habit.ui.common.model.MissionSuccessData
import com.habit.ui.screen.RefreshTokenState
import com.habit.ui.screen.setting.MemberSettingPageInfoUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.ZonedDateTime
import java.util.Calendar
import javax.inject.Inject

private const val TAG = "HomeViewModel"

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val healthConnectClient: HealthConnectClient,
    private val useCases: HomeUseCases
) : ViewModel() {
    private val _refreshTokenState = MutableStateFlow<RefreshTokenState>(RefreshTokenState.NotNeedReLogin)
    var refreshTokenState: StateFlow<RefreshTokenState> = _refreshTokenState.asStateFlow()

    //mission ui state
    private val _homeMissionUiState =
        MutableStateFlow<MissionUiState>(MissionUiState.MissionLoading)
    val homeMissionUiState: StateFlow<MissionUiState> = _homeMissionUiState.asStateFlow()

    val permissions = setOf(
        HealthPermission.getReadPermission(SleepSessionRecord::class),
        HealthPermission.getReadPermission(StepsRecord::class)
    )

    //member info ui state
    private val _memberHomeInfoUiState =
        MutableStateFlow<MemberHomeInfoState>(MemberHomeInfoState.MemberHomeInfoLoading)
    val memberHomeInfoUiState: StateFlow<MemberHomeInfoState> = _memberHomeInfoUiState.asStateFlow()

    //홈화면 데이터(코인, 연속일수, 미션 별 코인 수령 여부)
    private val _homeData: MutableStateFlow<HomeDataDto> =
        MutableStateFlow(HomeDataDto(-1, -1, false, false, false))
    val homeData: StateFlow<HomeDataDto> = _homeData.asStateFlow()

    //멤버 홈화면 정보(닉네임, 캐릭터 이미지)
    private val _memberHomeInfo: MutableStateFlow<MemberHomeInfoDto> = MutableStateFlow(
        MemberHomeInfoDto("", "")
    )
    val memberHomeInfoDto: StateFlow<MemberHomeInfoDto> = _memberHomeInfo.asStateFlow()

    // 수면 데이터 접근 퍼미션 상태
    private val _sleepPermissionState = MutableStateFlow<SleepPermissionState>(
        SleepPermissionState.PermissionStateLoading
    )
    val sleepPermissionState: StateFlow<SleepPermissionState> = _sleepPermissionState.asStateFlow()

    //미션 데이터
    private val _missionInfo: MutableStateFlow<MissionDto> =
        MutableStateFlow(MissionDto(-1, -1, -1, -1, "", ""))
    val missionInfo: StateFlow<MissionDto> = _missionInfo

    //수면 데이터
    private val _sleepSessionData = MutableStateFlow<SleepSessionDto>(
        SleepSessionDto(
            Duration.ZERO,
            Duration.ZERO,
            Duration.ZERO,
            Duration.ZERO,
            Duration.ZERO
        )
    )
    val sleepSessionData: StateFlow<SleepSessionDto> = _sleepSessionData.asStateFlow()

    //칼로리 총 량 데이터
    private val _totalCalorie = MutableStateFlow<Int>(0)
    val totalCalorie: StateFlow<Int> = _totalCalorie.asStateFlow()

    //운동리스트
    private val _exerciseList = MutableStateFlow<List<ExerciseDto>>(listOf())
    val exerciseList: StateFlow<List<ExerciseDto>> = _exerciseList.asStateFlow()

    //미션 성공 여부 데이터
    private val _missionSuccessData = MutableStateFlow<MissionSuccessData>(MissionSuccessData())
    val missionSuccessData: StateFlow<MissionSuccessData> = _missionSuccessData

    //걸음수 데이터
    private val _stepData = MutableStateFlow<Long>(0)
    val stepData: StateFlow<Long> = _stepData.asStateFlow()

    fun checkPermission() {
        viewModelScope.launch {
            val permissionState = healthConnectClient.permissionController.getGrantedPermissions()
                .containsAll(permissions)
            if (permissionState == true) {
                _sleepPermissionState.value = SleepPermissionState.PermissionAccepted
            } else {
                _sleepPermissionState.value = SleepPermissionState.PermissionNotAccepted
            }
        }
    }

    suspend fun tryWithPermissionsCheck(block: suspend () -> Unit) {
        viewModelScope.launch {
            //퍼미션이 이미 허용되었는지 확인합니다
            val permissionState = healthConnectClient.permissionController.getGrantedPermissions()
                .containsAll(permissions)
            //퍼미션 허용 되었으면 매개변수로 받은 block을 실행합니다
            try {
                if (permissionState) {
                    block()
                    _sleepPermissionState.emit(SleepPermissionState.PermissionAccepted)
                }else{
                    _sleepPermissionState.emit(SleepPermissionState.PermissionNotAccepted)
                }
            } catch (e: Exception) {
                SleepPermissionState.PermissionErr
            }
        }

    }

    //홈 body 에서 필요한 데이터
    //1. 홈화면데이터, 2. 미션내용, 4. 수면데이터, 5. 운동데이터, 6. 각미션 성공여부
    fun getHomeData() {
        viewModelScope.launch {

            val now = Calendar.getInstance() //현재 시각
            val beginTimeStandard = Calendar.getInstance().apply { //오늘 오전 3시
                set(
                    now.get(Calendar.YEAR),
                    now.get(Calendar.MONTH),
                    now.get(Calendar.DATE),
                    3,
                    0,
                    0
                )
            }

            val end = now.time // 끝 시간
            if (now.get(Calendar.HOUR_OF_DAY) >= 0 && now.get(Calendar.HOUR_OF_DAY) <= 2) {
                //현재 시각이 오전 0시 ~ 오전 2시 59분 이라면 전날 오전 3시 ~ 현재 까지의 데이터가 보여야 됨
                beginTimeStandard.add(Calendar.DATE, -1)
            }
            var begin = beginTimeStandard.time// 시작 시간
            Log.d(TAG, "getHomeData: begin ${begin}")

            //홈화면 데이터
            val homeDataRes = useCases.getHomeDataUseCase.invoke()
            homeDataRes.onSuccess {
                _homeData.emit(it)

                //미션 내용
                val missionDataRes = useCases.getMissionUseCase.invoke()
                _missionInfo.emit(missionDataRes)

                //수면 데이터
                var sleepDataRes: SleepSessionDto = SleepSessionDto(
                    Duration.ZERO, Duration.ZERO, Duration.ZERO, Duration.ZERO, Duration.ZERO
                )

//                tryWithPermissionsCheck {
//                    sleepDataRes = useCases.getSleepDataUseCase.invoke(ZonedDateTime.now())
//                    _sleepSessionData.value = sleepDataRes
//                    Log.d(TAG, "getHomeData: sleep Data ${sleepDataRes.totalSleepTime}")
//                }
                val permissionState = healthConnectClient.permissionController.getGrantedPermissions()
                    .containsAll(permissions)
                if (permissionState) {
                    val today = ZonedDateTime.now()
                    sleepDataRes = useCases.getSleepDataUseCase.invoke(today)
                    _sleepSessionData.value = sleepDataRes
                    _sleepPermissionState.emit(SleepPermissionState.PermissionAccepted)
                }else{
                    _sleepPermissionState.emit(SleepPermissionState.PermissionNotAccepted)
                }

                // 칼로리 데이터
                val totalCalorieRes = useCases.getTodaysCalorieUseCase.invoke(begin, end)
                _totalCalorie.emit(totalCalorieRes)

                //운동 리스트
                val exerciseListRes = useCases.getTodaysExerciseUseCase.invoke(begin, end)
                _exerciseList.emit(exerciseListRes)

                var missionSuccessState: MissionSuccessData = MissionSuccessData()

                //칼로리 미션 상태
                if (it.calorieMissionSuccess) {
                    missionSuccessState.calorieMissionSuccessState = MissionStateType.ALREADY_REWARDED
                } else {
                    val goalCalorie = missionDataRes.calorie
                    if (goalCalorie > totalCalorieRes) {
                        missionSuccessState.calorieMissionSuccessState = MissionStateType.NOT_ACHIEVED
                    } else {
                        missionSuccessState.calorieMissionSuccessState =
                            MissionStateType.REWARD_POSSIBLE
                    }
                }

                //수면 미션 상태
                if (it.sleepMissionSuccess) {
                    missionSuccessState.sleepMissionSuccessState = MissionStateType.ALREADY_REWARDED
                } else {
                    val goalTime = missionDataRes.hour * 60 + missionDataRes.minute
                    val sleepTimeTotal = sleepDataRes.totalSleepTime.toMinutes()
                    Log.d(TAG, "getHomeData: sleep -- ${sleepTimeTotal}")
                    if (goalTime > sleepTimeTotal) {
                        missionSuccessState.sleepMissionSuccessState = MissionStateType.NOT_ACHIEVED
                    } else {
                        missionSuccessState.sleepMissionSuccessState = MissionStateType.REWARD_POSSIBLE
                    }
                }

                //자유미션 상태
                if (it.freeMissionSuccess) {
                    missionSuccessState.freeMissionSuccessState = MissionStateType.ALREADY_REWARDED
                } else {
                    missionSuccessState.freeMissionSuccessState = MissionStateType.REWARD_POSSIBLE
                }
                _missionSuccessData.emit(missionSuccessState)
                _homeMissionUiState.value = MissionUiState.MissionLoaded

            }

            homeDataRes.onError {
                when (it.message) {
                    "required_re_login"->{
                        useCases.deleteAccessTokenUseCase.invoke()
                        _refreshTokenState.emit(RefreshTokenState.NeedReLogin)
                    }
                    else -> {
                        _homeMissionUiState.value = MissionUiState.MissionErr
                    }
                }
            }

        }
    }

    fun getMemberHomeInfo(){
        viewModelScope.launch {
            val res = useCases.getMemberHomeInfoUseCase.invoke()
            res.onSuccess {
                _memberHomeInfo.emit(it)
                _memberHomeInfoUiState.emit(MemberHomeInfoState.MemberHomeInfoLoaded)
            }
            res.onError {
                when (it.message) {
                    "required_re_login"->{
                        useCases.deleteAccessTokenUseCase.invoke()
                        _refreshTokenState.emit(RefreshTokenState.NeedReLogin)
                    }
                    else -> {
                        _memberHomeInfoUiState.emit(MemberHomeInfoState.MemberHomeInfoErr)
                    }
                }
            }
        }
    }

    fun postExerciseMissionSuccess() {
        viewModelScope.launch {
            val res = useCases.postExerciseMissionSuccessUseCase.invoke()
            res.onSuccess {
                getHomeData()
            }
            res.onError {
                when(it.message){
                    "required_re_login"->{
                        useCases.deleteAccessTokenUseCase.invoke()
                        _refreshTokenState.emit(RefreshTokenState.NeedReLogin)
                    }
                }
            }
        }
    }

    fun postSleepMissionSuccess() {
        viewModelScope.launch {
            val res = useCases.postSleepMissionSuccessUseCase.invoke()
            res.onSuccess {
                getHomeData()
            }
            res.onError {
                when(it.message){
                    "required_re_login"->{
                        useCases.deleteAccessTokenUseCase.invoke()
                        _refreshTokenState.emit(RefreshTokenState.NeedReLogin)
                    }
                }
            }
        }
    }

    fun postFreeMissionSuccess() {
        viewModelScope.launch {
            val res = useCases.postFreeMissionSuccessUseCase.invoke()
            res.onSuccess {
                getHomeData()
            }
            res.onError {
                when(it.message){
                    "required_re_login"->{
                        useCases.deleteAccessTokenUseCase.invoke()
                        _refreshTokenState.emit(RefreshTokenState.NeedReLogin)
                    }
                }
            }

        }
    }

    fun getStepData(){
        viewModelScope.launch {
            tryWithPermissionsCheck {
                _stepData.emit(useCases.getStepDataUseCase(ZonedDateTime.now()))
            }
        }
    }


}