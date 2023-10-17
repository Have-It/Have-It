package com.habit.presentation.screen.selectexercise

import androidx.health.services.client.data.LocationAvailability
import com.habit.data.ServiceState

//운동 선택 화면에서의 상태를 나타냄
sealed class SelectExerciseUiState {
    abstract val serviceState: ServiceState //health service의 상태
    abstract val isTrackingInAnotherApp: Boolean //다른 앱의 운동을 트래킹중인지 확인?
    abstract val requiredPermissions: List<String> //필요한 퍼미션 리스트

    data class Disconnected( //health service 연결안된 상태
        override val serviceState: ServiceState.Disconnected,
        override val isTrackingInAnotherApp: Boolean,
        override val requiredPermissions: List<String>
    ) : SelectExerciseUiState()

    data class Preparing( //health service 연결되었는 상태
        override val serviceState: ServiceState.Connected,
        override val isTrackingInAnotherApp: Boolean,
        override val requiredPermissions: List<String>,
        val hasExerciseCapabilities: Boolean
    ) : SelectExerciseUiState() {
        val locationAvailability: LocationAvailability =
            (serviceState as? ServiceState.Connected)?.locationAvailabilityState
                ?: LocationAvailability.UNKNOWN
    }
}