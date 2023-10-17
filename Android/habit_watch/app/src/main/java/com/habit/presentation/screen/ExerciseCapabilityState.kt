package com.habit.presentation.screen

sealed interface ExerciseCapabilityState {
    object Loading: ExerciseCapabilityState
    object Available: ExerciseCapabilityState
    object NotAvailable: ExerciseCapabilityState
}