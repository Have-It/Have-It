package com.habit.data.entity.request

import com.google.gson.annotations.SerializedName

data class KakaoLoginRequest(
    @SerializedName("token")
    val kakaoToken: String
)
