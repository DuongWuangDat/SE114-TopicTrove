package com.topic_trove.data.service

import com.topic_trove.data.model.EmailRequest
import com.topic_trove.data.model.RegisterRequest
import com.topic_trove.data.model.RegisterResponse
import com.topic_trove.data.model.SendEmailResponse
import com.topic_trove.data.model.UploadAvatarResponse
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface RegisterService {

    @POST("/api/v1/user/register")
    suspend fun register(@Body registerRequest: RegisterRequest): RegisterResponse

    @POST("/api/v1/service/sendEmail")
    suspend fun sendEmail(@Body emailRequest: EmailRequest): SendEmailResponse

    @Multipart
    @POST("/api/v1/upload/image")
    suspend fun uploadAvatar(
        @Part file: MultipartBody.Part
    ): UploadAvatarResponse
}