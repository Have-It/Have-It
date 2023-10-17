package com.habit.domain.useCase.main

import com.habit.domain.useCase.home.GetMissionUseCase

data class MainUseCases(
    val getMemberInfoUseCase: GetMemberInfoUseCase,
    val getMissionUseCase: GetMissionUseCase
)