package com.topic_trove.data.repositories

import com.topic_trove.data.model.CommentResponse
import com.topic_trove.data.model.CreateCommentRequest
import com.topic_trove.data.model.LikeRequest
import com.topic_trove.data.model.MessageResponse
import com.topic_trove.data.model.PostDetailResponse
import com.topic_trove.data.service.PostService
import retrofit2.Retrofit
import javax.inject.Inject

interface PostRepository {

    suspend fun likePost(id: String, likeRequest: LikeRequest): Result<MessageResponse>

    suspend fun deleteComment(id: String): Result<MessageResponse>

    suspend fun likeComment(
        id: String,
        likeRequest: LikeRequest,
    ): Result<MessageResponse>

    suspend fun createComment(createCommentRequest: CreateCommentRequest): Result<MessageResponse>

    suspend fun getPostById(id: String): Result<PostDetailResponse>
    suspend fun getCommentByPostId(id: String): Result<List<CommentResponse>>
}

class PostRepositoryImpl @Inject constructor(
    apiClient: Retrofit,
) : PostRepository {

    private val service = apiClient.create(PostService::class.java)
    override suspend fun likePost(
        id: String,
        likeRequest: LikeRequest
    ): Result<MessageResponse> {
        return try {
            val response = service.likePost(id, likeRequest)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteComment(id: String): Result<MessageResponse> {
        return try {
            val response = service.deleteComment(id)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun likeComment(
        id: String,
        likeRequest: LikeRequest
    ): Result<MessageResponse> {
        return try {
            val response = service.likeComment(id, likeRequest)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun createComment(createCommentRequest: CreateCommentRequest): Result<MessageResponse> {
        return try {
            val response = service.createComment(createCommentRequest)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getPostById(id: String): Result<PostDetailResponse> {
        return try {
            val response = service.getPostById(id)
            Result.success(response.data)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getCommentByPostId(id: String): Result<List<CommentResponse>> {
        return try {
            val response = service.getCommentByPostId(id)
            Result.success(response.data)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}