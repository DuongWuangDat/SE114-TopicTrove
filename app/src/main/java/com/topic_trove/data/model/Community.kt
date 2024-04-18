package com.topic_trove.data.model

data class Community(
    val id: String = "",
    val owner: String = "",
    val icon : String = "",
    val description: String = "",
    val rules: String = "",
    val communityName : String = "",
    val memberCount : Int = 0
)