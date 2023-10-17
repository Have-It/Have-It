package com.habit.data.entity.request

data class SaveMemberInfoRequest(
    val memberId: Int,
    val nickName: String,
    val profileImage: String?,
    val settingImage: String?,
    val grantType: String,
    val accessToken: String,
    val refreshToken: String,
    val refreshTokenExpirationTime: String
)