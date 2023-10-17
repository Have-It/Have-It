package com.habit.presentation.screen.exercisesummary

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.google.android.gms.wearable.CapabilityClient
import com.google.android.gms.wearable.Wearable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.tasks.await
import java.time.Duration
import javax.inject.Inject

@HiltViewModel
class ExerciseSummaryViewMode @Inject constructor(
    savedStateHandle: SavedStateHandle
): ViewModel() {
//    val uiState = MutableStateFlow(
//        SummaryScreenState(
//            averageHeartRate = (savedStateHandle.get<Float>("averageHeartRate")?:0)
//                .toDouble(),
//            totalCalories = (savedStateHandle.get<Float>("totalCalories")?:0)
//                .toDouble(),
//            elapsedTime = Duration.parse(savedStateHandle.get("elapsedTime")?:"PT0H0M50S"),
//        )
//    )
}