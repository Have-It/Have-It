package com.habit.ui.screen.home

interface MemberHomeInfoState {
    object MemberHomeInfoLoading: MemberHomeInfoState
    object MemberHomeInfoLoaded: MemberHomeInfoState
    object MemberHomeInfoErr: MemberHomeInfoState
}