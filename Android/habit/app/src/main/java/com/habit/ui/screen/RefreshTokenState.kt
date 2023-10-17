package com.habit.ui.screen

interface RefreshTokenState {
    object NeedReLogin: RefreshTokenState
    object NotNeedReLogin: RefreshTokenState
}