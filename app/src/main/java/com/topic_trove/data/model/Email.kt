package com.topic_trove.data.model

import com.google.gson.annotations.SerializedName

data class Email(
    @SerializedName("email")
    val email: String,
)