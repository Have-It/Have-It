package com.habit.data

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import androidx.health.services.client.data.ExerciseType
import androidx.health.services.client.data.LocationAvailability
import com.habit.data.model.HabitExerciseType
import com.habit.service.ExerciseService
import com.habit.service.ExerciseServiceState
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "HealthServicesRepositor"

class HealthServicesRepository @Inject constructor(
    @ApplicationContext private val applicationContext: Context,
    val exerciseClientManager: ExerciseClientManager,
    val coroutineScope: CoroutineScope
) {
    // 운동의 상태를 저장하는 변수
    private val exerciseService: MutableStateFlow<ExerciseService?> = MutableStateFlow(null)

    // HealthService의 상태를 나타내는 변수
    @Suppress("IfThenToElvis")
    val serviceState: StateFlow<ServiceState> = exerciseService.flatMapLatest { exerciseService ->
        //exerciseService 플로우에서 가장 최신의 업데이트 값을 통해 서비스 상태를 알아낸다
        if (exerciseService == null) { //가장 최신의 값이 null이라면 서비스가 연결되지 않은 상태
            flowOf(ServiceState.Disconnected)
        } else {
            exerciseService.exerciseServiceMonitor.exerciseServiceState.map {
                ServiceState.Connected(it)
            }
        }
    }.stateIn(
        coroutineScope,
        started = SharingStarted.Eagerly,
        initialValue = ServiceState.Disconnected
    )

    suspend fun hasExerciseCapability(exercise: ExerciseType): Boolean = getExerciseCapabilities(exercise) != null

    private suspend fun getExerciseCapabilities(exercise: ExerciseType) = exerciseClientManager.getExerciseCapabilities(exercise)

    suspend fun isExerciseInProgress(): Boolean =
        exerciseClientManager.exerciseClient.isExerciseInProgress()

    suspend fun isTrackingExerciseInAnotherApp(): Boolean =
        exerciseClientManager.exerciseClient.isTrackingExerciseInAnotherApp()

    fun prepareExercise() = coroutineScope.launch { exerciseService.value!!.prepareExercise() }

    @RequiresApi(Build.VERSION_CODES.Q)
    fun startExercise(exercise: HabitExerciseType) =
        coroutineScope.launch { exerciseService.value!!.startExercise(exercise) }

    fun pauseExercise() = coroutineScope.launch { exerciseService.value!!.pauseExercise() }

    @RequiresApi(Build.VERSION_CODES.Q)
    fun endExercise() = coroutineScope.launch { exerciseService.value!!.endExercise() }
    fun resumeExercise() = coroutineScope.launch { exerciseService.value!!.resumeExercise() }

    private val connection = object : android.content.ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            val binder = service as ExerciseService.LocalBinder
            binder.getService().let {
                exerciseService.value = it
            }
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            exerciseService.value = null
        }

    }

    //Health Service를 만든다
    fun createService() {
        Intent(applicationContext, ExerciseService::class.java).also { intent ->
            applicationContext.startService(intent) //health service를 시작
            applicationContext.bindService(
                intent,
                connection,
                Context.BIND_AUTO_CREATE
            ) //health service연결
        }
    }

}

/** 운동의 상태를 나타낸다, 서비스가 살아있는 동안에는 유지된다.**/
sealed class ServiceState {
    object Disconnected : ServiceState()
    data class Connected(
        val exerciseServiceState: ExerciseServiceState,
    ) : ServiceState() {
        val locationAvailabilityState: LocationAvailability =
            exerciseServiceState.locationAvailability
    }
}


