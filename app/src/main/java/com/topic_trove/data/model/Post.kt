package com.topic_trove.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.Date


data class Post (
    var id : String = "",
    var authorID : String = "",
    var authorName: String ="",
    var communityID: String = "",
    var communityName: String ="",
    var content: String = "",
    var title: String = "",
    var imageUrl: String = "",
    var avatar: String ="",
    var createdAt: Date = Date(),
    var interestCount: Int = 0,
    var isLike: Boolean = false,
    var commentCount: Int = 0
)