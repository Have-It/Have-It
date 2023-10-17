package com.habit.ui.screen.setting

sealed interface SettingUiState {
    object NotLogouted: SettingUiState
    object Logout: SettingUiState
}