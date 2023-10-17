package com.habit.presentation.screen.exercisesummary

sealed interface SummaryState {
    object Loading : SummaryState
    object SummaryValueLoaded : SummaryState
}