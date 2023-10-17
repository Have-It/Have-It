package com.habit.ui.screen.login

sealed interface LoginUiState {
    object NotLogined : LoginUiState
    object LoginSuccess: LoginUiState
    object Loading: LoginUiState
    object LoginFail: LoginUiState
}