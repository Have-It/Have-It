package com.habit.data.entity.response

data class MissionResponse (
    val userId: Int,
    val calorie: Int,
    val hour: Int,
    val minute: Int,
    val freeMissionType: String,
    val freeMissionDetail: String
)