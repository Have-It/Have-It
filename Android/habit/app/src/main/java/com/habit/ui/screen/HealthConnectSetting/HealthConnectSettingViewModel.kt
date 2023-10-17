package com.habit.ui.screen.HealthConnectSetting

import android.content.Context
import android.util.Log
import androidx.activity.result.contract.ActivityResultContract
import androidx.compose.runtime.mutableStateOf
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.PermissionController
import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.records.SleepSessionRecord
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.habit.di.ApplicationClass
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "HealthConnectSettingVie"

@HiltViewModel
class HealthConnectSettingViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _healthConnectState = MutableStateFlow<HealthConnectState>(
        HealthConnectState.Loading
    )
    val healthConnectState: StateFlow<HealthConnectState> = _healthConnectState.asStateFlow()

    fun getHealthConnectState() {
        val healthConnectApplicationState = HealthConnectClient.getSdkStatus(context)
        Log.d(TAG, "getHealthConnectState: ${healthConnectApplicationState}")
        if (healthConnectApplicationState == HealthConnectClient.SDK_AVAILABLE) {
            _healthConnectState.value = HealthConnectState.HealthConnectReady
        } else if (healthConnectApplicationState == HealthConnectClient.SDK_UNAVAILABLE_PROVIDER_UPDATE_REQUIRED) {
            _healthConnectState.value = HealthConnectState.HealthConnectNotInstalled
        } else if (healthConnectApplicationState == HealthConnectClient.SDK_UNAVAILABLE) {
            _healthConnectState.value = HealthConnectState.HealthConnectNotSupport
        }
//        when (HealthConnectClient.getSdkStatus(context)) {
//            HealthConnectClient.SDK_AVAILABLE ->
//            HealthConnectClient.SDK_UNAVAILABLE_PROVIDER_UPDATE_REQUIRED -> NotInstalledMessage()
//            HealthConnectClient.SDK_UNAVAILABLE -> NotSupportedMessage()
//        }
    }

}