package com.habit.ui.screen.setting

import com.habit.ui.screen.home.SleepPermissionState

interface StepPermissionState {
    object StepPermissionAccepted: StepPermissionState
    object StepPermissionNotAccepted: StepPermissionState
    object StepPermissionStateLoading: StepPermissionState
}