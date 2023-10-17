package com.habit.ui.screen.HealthConnectSetting


sealed interface HealthConnectState {
    object HealthConnectNotInstalled: HealthConnectState // 헬스커넥트 설치 안됨
    object HealthConnectNotSupport: HealthConnectState // 헬스커넥트 지원안되는 sdk
    ///object HealthConnectPermissionNotAccepted: HealthConnectState // 퍼미션 허용 안됨
    object HealthConnectReady: HealthConnectState // 헬스커넥트 준비완료
    object Loading: HealthConnectState
}