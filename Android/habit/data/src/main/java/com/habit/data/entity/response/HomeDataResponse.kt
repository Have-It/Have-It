package com.habit.data.entity.response

import com.google.gson.annotations.SerializedName

data class HomeDataResponse (
    @SerializedName("totalSuccess")
    val totalSuccess: Int,
    @SerializedName("coin")
    val coin: Int,
    @SerializedName("mission1Success")
    val calorieMissionSuccess: Boolean,
    @SerializedName("mission2Success")
    val sleepMissionSuccess: Boolean,
    @SerializedName("mission3Success")
    val freeMissionSuccess: Boolean
)