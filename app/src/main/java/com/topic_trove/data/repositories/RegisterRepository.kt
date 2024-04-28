package com.topic_trove.data.repositories

import android.util.Log
import com.topic_trove.data.model.Email
import com.topic_trove.data.model.RegisterResponse
import com.topic_trove.data.model.SendEmailResponse
import com.topic_trove.data.model.User
import com.topic_trove.data.service.RegisterService
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
}

class RegisterRepositoryImpl @Inject constructor(
    apiClient: Retrofit,
) : RegisterRepository {

    private val service = apiClient.create(RegisterService::class.java)
    override suspend fun register(
        name: String, phone: String, email: String, password: String
    ): Result<RegisterResponse> {
        return try {
            val registerResponse = service.register(User(name, email, password, phone, ""))
            Result.success(registerResponse)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun sendEmail(email: String): Result<SendEmailResponse> {
        return try {
            val sendEmailResponse = service.sendEmail(Email(email))
            Result.success(sendEmailResponse)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}