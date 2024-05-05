package com.topic_trove.data.repositories

import com.topic_trove.data.model.EmailRequest
import com.topic_trove.data.model.LoginRequest
import com.topic_trove.data.model.RegisterRequest
import com.topic_trove.data.model.RegisterResponse
import com.topic_trove.data.model.SendEmailResponse
import com.topic_trove.data.service.AuthService
import retrofit2.Retrofit
import javax.inject.Inject

interface RegisterRepository {

    suspend fun register(
        name: String,
        phone: String,
        email: String,
        password: String
    ): Result<RegisterResponse>

    suspend fun sendEmail(email: String): Result<SendEmailResponse>

    suspend fun login(email: String, password: String): Result<RegisterResponse>
}

class RegisterRepositoryImpl @Inject constructor(
    apiClient: Retrofit,
) : RegisterRepository {

    private val service = apiClient.create(AuthService::class.java)
    override suspend fun register(
        name: String, phone: String, email: String, password: String
    ): Result<RegisterResponse> {
        return try {
            val registerResponse =
                service.register(RegisterRequest(name, email, password, phone, ""))
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

    override suspend fun login(email: String, password: String): Result<RegisterResponse> {
        return try {
            val response = service.login(LoginRequest(email, password))
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}