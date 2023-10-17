package com.habit.presentation.screen.selectexercise

sealed interface PermissionState {
    object PermissionAccepted: PermissionState
    object PermissionNotAccepted: PermissionState
    object PermissionLoading: PermissionState
}