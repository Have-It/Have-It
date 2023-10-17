package com.habit.domain.model.mission

data class MissionDto(
    val userId: Int = -1,
    val calorie: Int,
    val hour: Int,
    val minute: Int,
    val freeMissionType: String,
    val freeMissionDetail: String
)