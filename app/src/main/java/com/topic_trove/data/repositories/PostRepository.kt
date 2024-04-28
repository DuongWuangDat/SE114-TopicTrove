package com.topic_trove.data.repositories

import com.topic_trove.data.model.MessageResponse
import com.topic_trove.data.model.CreateCommentRequest
import com.topic_trove.data.model.LikeCommentRequest
import com.topic_trove.data.model.LikePostRequest
import com.topic_trove.data.model.PostDetailResponse
import com.topic_trove.data.service.PostService
import retrofit2.Retrofit
import javax.inject.Inject

interface PostRepository {

    suspend fun likePost(id: String, likePostRequest: LikePostRequest): Result<MessageResponse>

    suspend fun deleteComment(id: String): Result<MessageResponse>

    suspend fun likeComment(
        id: String,
        likeCommentRequest: LikeCommentRequest
    ): Result<MessageResponse>

    suspend fun createComment(createCommentRequest: CreateCommentRequest): Result<MessageResponse>

    suspend fun getPostById(id: String): Result<PostDetailResponse>
}

class PostRepositoryImpl @Inject constructor(
    apiClient: Retrofit,
) : PostRepository {

    private val service = apiClient.create(PostService::class.java)
    override suspend fun likePost(
        id: String,
        likePostRequest: LikePostRequest
    ): Result<MessageResponse> {
        return try {
            val response = service.likePost(id, likePostRequest)
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
        likeCommentRequest: LikeCommentRequest
    ): Result<MessageResponse> {
        return try {
            val response = service.likeComment(id, likeCommentRequest)
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

}