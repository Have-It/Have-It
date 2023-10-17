package com.habit.data.entity.response

import com.google.gson.annotations.SerializedName

data class MemberInfoResponse (
    @SerializedName("memberId")
    val memberId: Int,
    @SerializedName("nickName")
    val nickName: String,
    @SerializedName("profileImage")
    val profileImage: String,
    @SerializedName("settingImage")
    val settingImage: String,
    @SerializedName("grantType")
    val grantType: String,
    @SerializedName("accessToken")
    val accessToken: String,
    @SerializedName("refreshToken")
    val refreshToken: String,
    @SerializedName("refreshTokenExpirationTime")
    val refreshTokenExpirationTime: String
)