package com.habit.data.entity.request

data class MissionRequest (
    val userId: Int,
    val calorie: Int,
    val hour: Int,
    val minute: Int,
    val freeMissionType: String,
    val freeMissionDetail: String
)