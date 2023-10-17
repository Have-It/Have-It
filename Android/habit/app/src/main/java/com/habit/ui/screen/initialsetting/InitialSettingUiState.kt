package com.habit.ui.screen.initialsetting

sealed interface InitialSettingUiState {
    object MissionExist: InitialSettingUiState
    object MissionNotExist: InitialSettingUiState
    object Loading: InitialSettingUiState
}