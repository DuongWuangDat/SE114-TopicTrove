package com.topic_trove.data.model

import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    @SerializedName("username")
    val username: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("phoneNumber")
    val phoneNumber: String,
    @SerializedName("avatar")
    val avatar: String,
)
