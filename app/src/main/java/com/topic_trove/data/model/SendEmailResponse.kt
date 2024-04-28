package com.topic_trove.data.model

import com.google.gson.annotations.SerializedName

data class SendEmailResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("userCode")
    val userCode: String,
)