package com.topic_trove.data.model

import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("refresh_token")
    val refreshToken: String,
    val data: User,
)
