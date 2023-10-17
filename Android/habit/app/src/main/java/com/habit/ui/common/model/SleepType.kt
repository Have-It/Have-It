package com.habit.ui.common.model

import androidx.annotation.StringRes
import androidx.compose.ui.res.stringResource
import com.habit.R

enum class SleepType(@StringRes val cardText : Int) {
    AWAKE(R.string.home_bottom_sheet_sleep_awake),
    REM(R.string.home_bottom_sheet_sleep_rem),
    LIGHT(R.string.home_bottom_sheet_sleep_light),
    DEEP(R.string.home_bottom_sheet_sleep_deep)
}