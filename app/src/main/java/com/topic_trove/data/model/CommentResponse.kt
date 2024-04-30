package com.topic_trove.data.model

import com.google.gson.annotations.SerializedName
import java.util.Date

data class CommentResponse(
    val comment: Comment?,
    val children: List<CommentResponse>?,
)

data class Comment(
    @SerializedName("_id")
    val id: String,
    val interestUserList: List<String>,
    val author: Author?,
    val post: String?,
    val content: String?,
    val interestCount: Int?,
    val disinterestCount: Int?,
    val parentComment: String?,
    val createdAt: Date?,
    val updatedAt: Date?,
)
