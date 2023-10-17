package com.habit.ui.screen.home

sealed interface SleepPermissionState {
    object PermissionAccepted: SleepPermissionState
    object PermissionNotAccepted: SleepPermissionState
    object PermissionStateLoading: SleepPermissionState
    object PermissionErr: SleepPermissionState
}