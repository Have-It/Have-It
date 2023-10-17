package com.habit.ui.screen.setting

interface StatUiState {
    object StatLoading: StatUiState
    object StatLoaded: StatUiState
    object StatErr: StatUiState
}