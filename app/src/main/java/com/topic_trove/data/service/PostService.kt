package com.topic_trove.data.service

import com.topic_trove.data.model.CommonResponse
import com.topic_trove.data.model.MessageResponse
import com.topic_trove.data.model.CreateCommentRequest
import com.topic_trove.data.model.LikeCommentRequest
import com.topic_trove.data.model.LikePostRequest
import com.topic_trove.data.model.PostDetailResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface PostService {

    @PATCH("/api/v1/post/likepost/{id}")
    suspend fun likePost(
        @Path("id") id: String,
        @Body likePostRequest: LikePostRequest,
    ): MessageResponse

    @DELETE("/api/v1/delete/{id}")
    suspend fun deleteComment(
        @Path("id") id: String,
    ): MessageResponse

    @PATCH("/api/v1/comment/update/{id}")
    suspend fun likeComment(
        @Path("id") id: String,
        @Body likePost: LikeCommentRequest,
    ): MessageResponse

    @POST("/api/v1/comment/create")
    suspend fun createComment(
        @Body createCommentRequest: CreateCommentRequest,
    ): MessageResponse

    @GET("/api/v1/post/findbyid/{id}")
    suspend fun getPostById(
        @Path("id") id: String
    ): CommonResponse<PostDetailResponse>
}