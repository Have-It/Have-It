package com.habit.data.entity.response

import com.google.gson.annotations.SerializedName

data class StatResponse(
    @SerializedName("characterWeight")
    val characterWeight: Int,
    @SerializedName("characterDarkcircle")
    val characterDarkcircle: Int
)