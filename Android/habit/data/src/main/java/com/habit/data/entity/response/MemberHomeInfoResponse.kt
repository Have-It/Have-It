package com.habit.data.entity.response

import com.google.gson.annotations.SerializedName

data class MemberHomeInfoResponse(
    @SerializedName("nickname")
    val nickName: String,
    @SerializedName("profileImage")
    val profileImage: String
)