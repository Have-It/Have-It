package com.habit.data.entity.response

import com.google.gson.annotations.SerializedName

data class DailyMissionValueSaveResponse (
    @SerializedName("memberId")
    val memberId: Int,
    @SerializedName("recordDate")
    val recordDate: String,
    @SerializedName("totalKcal")
    val totalKcal: Int,
    @SerializedName("totalWalk")
    val totalWalk: Int
)