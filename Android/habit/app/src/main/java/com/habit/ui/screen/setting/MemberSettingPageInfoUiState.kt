package com.habit.ui.screen.setting

interface MemberSettingPageInfoUiState {
    object MemberSettingInfoLoading: MemberSettingPageInfoUiState
    object MemberSettingInfoLoaded: MemberSettingPageInfoUiState
    object MemberHomeInfoErr: MemberSettingPageInfoUiState
}