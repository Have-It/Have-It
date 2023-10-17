package com.habit.presentation.screen.startexercise

import androidx.health.services.client.data.LocationAvailability
import com.habit.data.ServiceState

sealed class StartExerciseState {
    abstract val serviceState: ServiceState
    abstract val isTrackingInAnotherApp: Boolean
    abstract val requiredPermissions: List<String>

    data class Disconnected(
        override val serviceState: ServiceState.Disconnected,
        override val isTrackingInAnotherApp: Boolean,
        override val requiredPermissions: List<String>
    ) : StartExerciseState()

    data class Preparing(
        override val serviceState: ServiceState.Connected,
        override val isTrackingInAnotherApp: Boolean,
        override val requiredPermissions: List<String>,
        val hasExerciseCapabilities: Boolean
    ) : StartExerciseState() {
        val locationAvailability: LocationAvailability =
            (serviceState as? ServiceState.Connected)?.locationAvailabilityState
                ?: LocationAvailability.UNKNOWN
    }

}