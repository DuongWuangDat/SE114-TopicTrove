package com.topic_trove.data.model

import com.google.gson.annotations.SerializedName
import java.util.Date

data class PostDetailResponse(
    @SerializedName("_id")
    val id: String,
    val author: Author?,
    val communityId: CommunityId?,
    val title: String?,
    val content: List<Content>?,
    val interestUserList: List<String>?,
    val interestCount: Int?,
    val disinterestCount: Int?,
    val createdAt: Date?,
    val updatedAt: Date?,
    val commentCount: Int?,
)

data class Author(
    @SerializedName("_id")
    val id: String?,
    val username: String?,
    val email: String?,
    val phoneNumber: String?,
    val avatar: String?,
//    val communities: List<Community>,
    val createdAt: Date?,
    val updatedAt: Date?,
)

data class CommunityId(
    @SerializedName("_id")
    val id: String?,
    val owner: String?,
    val icon: String?,
    val description: String?,
    val rules: String?,
    val communityName: String?,
    val memberCount: Int?,
    val createdAt: Date?,
    val updatedAt: Date?,
)

data class Content(
    @SerializedName("_id")
    val id: String,
    val body: String,
    val typeContent: String,
)