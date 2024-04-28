package com.topic_trove.data.model

data class User(
    val id: String = "",
    val username: String = "",
    val email: String = "",
    val phoneNumber: String = "",
    val avatar: String = "",
    val communities: List<Community> = emptyList(),
)
