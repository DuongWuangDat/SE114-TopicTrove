package com.topic_trove.ui.modules.postdetailscreen

import com.topic_trove.data.model.Post
import java.io.Serializable
import java.util.Date

data class PostDetailUiState(
    val owner: Boolean = false,
    val isCommunityOwner: Boolean = false,
    val post: Post = Post(),
    val comments: List<Comment> = listOf(),
) : Serializable

data class Comment(
    val id: String = "",
    val authorName: String = "",
    val content: String = "",
    val createdAt: Date = Date(),
    val avatar: String = "",
    val interestCount: Int = 0,
    val liked: Boolean = false,
    val owner: Boolean = false,
    val replies: List<Comment> = listOf(),
) : Serializable
