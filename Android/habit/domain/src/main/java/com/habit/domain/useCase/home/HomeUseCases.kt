package com.habit.domain.useCase.home

import com.habit.domain.useCase.setting.DeleteAccessTokenUseCase

data class HomeUseCases(
    val getSleepDataUseCase: GetSleepDataUseCase,
    val getMissionUseCase: GetMissionUseCase,
    val getHomeDataUseCase: GetHomeDataUseCase,
    val postSleepMissionSuccessUseCase: PostSleepMissionSuccessUseCase,
    val postExerciseMissionSuccessUseCase: PostExerciseMissionSuccessUseCase,
    val postFreeMissionSuccessUseCase: PostFreeMissionSuccessUseCase,
    val getAllExerciseDataUseCase: GetAllExerciseDataUseCase,
    val getTodaysCalorieUseCase: GetTodaysCalorieUseCase,
    val getTodaysExerciseUseCase: GetTodaysExerciseUseCase,
    val getMemberHomeInfoUseCase: GetMemberHomeInfoUseCase,
    val deleteAccessTokenUseCase: DeleteAccessTokenUseCase,
    val getStepDataUseCase: GetStepDataUseCase
)