package com.habit.domain.model.user

data class MemberInfoDto(
    val memberId: Int = -1,
    val nickName: String = "",
    val profileImage: String? = "",
    val settingImage: String? = "",
    val grantType: String = "",
    val accessToken: String = "",
    val refreshToken: String = "",
    val refreshTokenExpirationTime: String = ""
)