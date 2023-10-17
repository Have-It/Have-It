package com.habit.app

sealed interface ConnectState {
    object Loading: ConnectState
    object NotConnected: ConnectState
    object Connected: ConnectState
}