package com.habit.presentation.screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.habit.data.HealthServicesRepository
import com.habit.data.model.HabitExerciseType
import com.habit.presentation.screen.exercisesummary.SummaryScreenState
import com.habit.presentation.screen.exercisesummary.SummaryState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.Duration
import javax.inject.Inject

private const val TAG = "MainViewModel"
@HiltViewModel
class MainViewModel @Inject constructor(
    private val healthServicesRepository: HealthServicesRepository
) : ViewModel() {
    private val _selectedExcercise = MutableStateFlow<HabitExerciseType>(HabitExerciseType.WALK)
    val selectedExcercise: StateFlow<HabitExerciseType> = _selectedExcercise.asStateFlow()

    private var _exerciseCapability =
        MutableStateFlow<ExerciseCapabilityState>(ExerciseCapabilityState.Loading)
    val exerciseCapability: StateFlow<ExerciseCapabilityState> = _exerciseCapability.asStateFlow()

    private var _summaryValue = MutableStateFlow<SummaryScreenState>(
        SummaryScreenState(
            averageHeartRate = -1.0,
            totalCalories = -1.0,
            elapsedTime = Duration.ZERO
        )
    )
    val summaryValue: StateFlow<SummaryScreenState> = _summaryValue.asStateFlow()

    private var _summaryValueState = MutableStateFlow<SummaryState>(SummaryState.Loading)
    val summaryValueState: StateFlow<SummaryState> = _summaryValueState.asStateFlow()

    fun selectExcercise(exercise: HabitExerciseType) {
        viewModelScope.launch {
            if (healthServicesRepository.hasExerciseCapability(exercise.exerciseType)) {
                _exerciseCapability.emit(ExerciseCapabilityState.Available)
            } else {
                _exerciseCapability.emit(ExerciseCapabilityState.NotAvailable)
            }
            _selectedExcercise.emit(exercise)
        }
    }

    fun resetCapability() {
        _exerciseCapability.value = ExerciseCapabilityState.Loading
    }

    fun setSummary(summaryValue: SummaryScreenState) {
        viewModelScope.launch {
            Log.d(TAG, "setSummary: 넣음 ${summaryValue.elapsedTime} ${summaryValue.totalCalories}")
            _summaryValue.emit(summaryValue)
            _summaryValueState.emit(SummaryState.SummaryValueLoaded)
        }
    }

    fun resetSummary() {
        viewModelScope.launch {
            _summaryValue.emit(
                SummaryScreenState(
                    averageHeartRate = -1.0,
                    totalCalories = -1.0,
                    elapsedTime = Duration.ZERO
                )
            )
            _summaryValueState.emit(SummaryState.Loading)
        }

    }
}