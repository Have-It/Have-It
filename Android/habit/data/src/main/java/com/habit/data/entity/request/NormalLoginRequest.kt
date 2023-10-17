package com.habit.data.entity.request

import com.google.gson.annotations.SerializedName

data class NormalLoginRequest(
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String
)