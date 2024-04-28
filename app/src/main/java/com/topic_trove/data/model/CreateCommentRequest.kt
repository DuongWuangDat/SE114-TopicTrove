package com.topic_trove.data.model

data class CreateCommentRequest(
    val author: String,
    val post: String,
    val content: String,
    val parentComment: String?,
)
