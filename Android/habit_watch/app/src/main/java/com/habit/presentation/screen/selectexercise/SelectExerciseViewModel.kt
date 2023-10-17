package com.habit.presentation.screen.selectexercise

import android.Manifest
import androidx.health.services.client.data.ExerciseType
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.habit.data.HealthServicesRepository
import com.habit.data.ServiceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class SelectExerciseViewModel @Inject constructor(
    private val healthServicesRepository: HealthServicesRepository
) : ViewModel() {
    init {
        //헬스 서비스를 만든다
        healthServicesRepository.createService()

        viewModelScope.launch {
            healthServicesRepository.serviceState.filter { it is ServiceState.Connected }.first()
            // 운동 준비중 상태로 변경
            healthServicesRepository.prepareExercise()
        }
    }

    private val _permissionState = MutableStateFlow<PermissionState>(
        PermissionState.PermissionLoading
    )
    val permissionState: StateFlow<PermissionState> = _permissionState.asStateFlow()

    val uiState: StateFlow<SelectExerciseUiState> = healthServicesRepository.serviceState.map {
        val isTrackingInAnotherApp = healthServicesRepository.isTrackingExerciseInAnotherApp()
        val hasExerciseCapabilities = healthServicesRepository.hasExerciseCapability(ExerciseType.WALKING)
        toUiState(
            serviceState = it,
            isTrackingInAnotherApp = isTrackingInAnotherApp,
            hasExerciseCapabilities = hasExerciseCapabilities
        )
    }.stateIn(
        viewModelScope,
        started = SharingStarted.WhileSubscribed(5.seconds),
        initialValue = toUiState(healthServicesRepository.serviceState.value)
    )

    private fun toUiState(
        serviceState: ServiceState,
        isTrackingInAnotherApp: Boolean = false,
        hasExerciseCapabilities: Boolean = true
    ): SelectExerciseUiState{
        return if (serviceState is ServiceState.Disconnected) {
            SelectExerciseUiState.Disconnected(serviceState, isTrackingInAnotherApp, permissions)
        } else {
            SelectExerciseUiState.Preparing(
                serviceState = serviceState as ServiceState.Connected,
                isTrackingInAnotherApp = isTrackingInAnotherApp,
                requiredPermissions = permissions,
                hasExerciseCapabilities = hasExerciseCapabilities,
            )
        }
    }

    fun setPermissionState(permissionState: PermissionState){
        _permissionState.value = permissionState
    }

    companion object {
        val permissions = listOf(
            Manifest.permission.BODY_SENSORS,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACTIVITY_RECOGNITION
        )
    }
}