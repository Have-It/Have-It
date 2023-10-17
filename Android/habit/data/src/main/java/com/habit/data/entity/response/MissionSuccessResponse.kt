package com.habit.data.entity.response

import com.google.gson.annotations.SerializedName

data class MissionSuccessResponse (
    @SerializedName("message")
    val message: String,
    @SerializedName("mission1Success")
    val calorieMissionSuccess: Boolean,
    @SerializedName("mission2Success")
    val sleepMissionSuccess: Boolean,
    @SerializedName("mission3Success")
    val freeMissionSuccess: Boolean,
    @SerializedName("coin")
    val coin: Int
)