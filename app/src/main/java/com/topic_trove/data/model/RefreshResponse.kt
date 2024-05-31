package com.topic_trove.data.model

import com.google.gson.annotations.SerializedName

data class RefreshResponse(
    @SerializedName("access_token")
    val accessToken: String
)
