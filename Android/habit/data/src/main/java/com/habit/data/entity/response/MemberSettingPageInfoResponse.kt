package com.habit.data.entity.response

import com.google.gson.annotations.SerializedName

data class MemberSettingPageInfoResponse(
    @SerializedName("nickname")
    val nickName: String,
    @SerializedName("settingImage")
    val settingPageProfileImage: String
)