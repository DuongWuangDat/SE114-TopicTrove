package com.topic_trove.data.service

import com.topic_trove.data.model.EmailRequest
import com.topic_trove.data.model.LoginRequest
import com.topic_trove.data.model.RegisterRequest
import com.topic_trove.data.model.RegisterResponse
import com.topic_trove.data.model.SendEmailResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("/api/v1/user/register")
    suspend fun register(@Body registerRequest: RegisterRequest): RegisterResponse

    @POST("/api/v1/service/sendEmail")
    suspend fun sendEmail(@Body emailRequest: EmailRequest): SendEmailResponse

    @POST("/api/v1/user/login")
    suspend fun login(@Body loginRequest: LoginRequest): RegisterResponse
}