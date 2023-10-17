package com.habit.domain.model.home

data class HomeDataDto(
    val totalSuccess: Int,
    val coin: Int,
    val calorieMissionSuccess: Boolean,
    val sleepMissionSuccess: Boolean,
    val freeMissionSuccess: Boolean
)