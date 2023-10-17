package com.habit.ui.screen.home

interface MissionUiState {
    object MissionLoading: MissionUiState
    object MissionLoaded: MissionUiState
    object MissionErr: MissionUiState
}