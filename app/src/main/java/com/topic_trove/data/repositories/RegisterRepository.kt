package com.topic_trove.data.repositories

import com.topic_trove.data.model.EmailRequest
import com.topic_trove.data.model.RegisterRequest
import com.topic_trove.data.model.RegisterResponse
import com.topic_trove.data.model.SendEmailResponse
import com.topic_trove.data.model.UploadAvatarResponse
import com.topic_trove.data.service.RegisterService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Retrofit
import java.io.File
import javax.inject.Inject

interface RegisterRepository {

    suspend fun register(
        name: String,
        phone: String,
        email: String,
        password: String,
        avatar: String,
    ): Result<RegisterResponse>

    suspend fun sendEmail(email: String): Result<SendEmailResponse>

    suspend fun uploadAvatar(file: File): Result<UploadAvatarResponse>
}

class RegisterRepositoryImpl @Inject constructor(
    apiClient: Retrofit,
) : RegisterRepository {

    private val service = apiClient.create(RegisterService::class.java)
    override suspend fun register(
        name: String, phone: String, email: String, password: String, avatar: String
    ): Result<RegisterResponse> {
        return try {
            val registerResponse =
                service.register(RegisterRequest(name, email, password, phone, avatar))
            Result.success(registerResponse)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun sendEmail(email: String): Result<SendEmailResponse> {
        return try {
            val sendEmailResponseRequest = service.sendEmail(EmailRequest(email))
            Result.success(sendEmailResponseRequest)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun uploadAvatar(file: File): Result<UploadAvatarResponse> {
        return try {
            val requestBody = RequestBody.create("*/*".toMediaTypeOrNull(), file)
            val response = service.uploadAvatar(
                MultipartBody.Part.createFormData(
                    "image",
                    file.name,
                    requestBody
                )
            )
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}