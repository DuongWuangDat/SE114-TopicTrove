package com.topic_trove.data.service

import com.topic_trove.data.model.EmailRequest
import com.topic_trove.data.model.RegisterResponse
import com.topic_trove.data.model.SendEmailResponse
import com.topic_trove.data.model.RegisterRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterService {

    @POST("/api/v1/user/register")
    suspend fun register(@Body registerRequest: RegisterRequest): RegisterResponse

    @POST("/api/v1/service/sendEmail")
    suspend fun sendEmail(@Body emailRequest: EmailRequest): SendEmailResponse
}