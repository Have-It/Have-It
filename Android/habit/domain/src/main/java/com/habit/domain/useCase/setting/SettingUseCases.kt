package com.habit.domain.useCase.setting

import com.habit.domain.useCase.home.GetStepDataUseCase

data class SettingUseCases (
    val deleteKakaoTokenUseCase: DeleteKakaoTokenUseCase,
    val deleteNormalLoginInfoUseCase: DeleteNormalLoginInfoUseCase,
    val logoutUseCase: LogoutUseCase,
    val getMemberSettingPageInfoUseCase: GetMemberSettingPageInfoUseCase,
    val getStatUseCase: GetStatUseCase,
    val deleteAccessTokenUseCase: DeleteAccessTokenUseCase,
    val getStepDataUseCase: GetStepDataUseCase,
    val putStepDataUseCase: PutStepDataUseCase
)