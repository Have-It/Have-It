package com.habit.domain.model.mission

data class MissionSuccessDto(
    val message: String,
    val calorieMissionSuccess: Boolean,
    val sleepMissionSuccess: Boolean,
    val freeMissionSuccess: Boolean,
)