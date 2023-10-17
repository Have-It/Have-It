package com.habit.presentation.screen.exercise

import android.util.Log
import androidx.health.services.client.data.ExerciseState
import com.habit.data.ServiceState
import com.habit.presentation.screen.exercisesummary.SummaryScreenState
import com.habit.service.ExerciseServiceState
import java.time.Duration

private const val TAG = "ExerciseScreenState"
data class ExerciseScreenState(
    val hasExerciseCapabilities: Boolean,
    val isTrackingAnotherExercise: Boolean,
    val serviceState: ServiceState,
    val exerciseState: ExerciseServiceState?
) {
    fun toSummary(): SummaryScreenState {
        val exerciseMetrics = exerciseState?.exerciseMetrics
        val averageHeartRate = exerciseMetrics?.heartRateAverage ?: Double.NaN
      //  val totalDistance = exerciseMetrics?.distance ?: 0.0
        val totalCalories = exerciseMetrics?.calories ?: Double.NaN
        val duration = exerciseState?.activeDurationCheckpoint?.activeDuration ?: Duration.ZERO
        Log.d(TAG, "toSummary: ${exerciseState?.activeDurationCheckpoint?.activeDuration }")
        return SummaryScreenState(averageHeartRate, totalCalories, duration)
    }

    val isEnding: Boolean
        get() = exerciseState?.exerciseState?.isEnding == true

    val isEnded: Boolean
        get() = exerciseState?.exerciseState?.isEnded == true

    val isPaused: Boolean
        get() = exerciseState?.exerciseState?.isPaused == true

    val isActive: Boolean
        get() = exerciseState?.exerciseState == ExerciseState.ACTIVE

}