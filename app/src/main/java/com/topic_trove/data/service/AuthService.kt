package com.topic_trove.data.service

import com.topic_trove.data.model.EmailRequest
import com.topic_trove.data.model.LoginRequest
import com.topic_trove.data.model.RefreshResponse
import com.topic_trove.data.model.RegisterRequest
import com.topic_trove.data.model.RegisterResponse
import com.topic_trove.data.model.SendEmailResponse
import com.topic_trove.data.model.UploadAvatarResponse
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface AuthService {

    @POST("/api/v1/user/register")
    suspend fun register(@Body registerRequest: RegisterRequest): RegisterResponse

    @POST("/api/v1/service/sendEmail")
    suspend fun sendEmail(@Body emailRequest: EmailRequest): SendEmailResponse

    @POST("/api/v1/user/login")
    suspend fun login(@Body loginRequest: LoginRequest): RegisterResponse

    @GET("/api/v1/token/refresh-token")
    suspend fun refresh(): RefreshResponse

    @Multipart
    @POST("/api/v1/upload/image")
    suspend fun uploadAvatar(
        @Part file: MultipartBody.Part
    ): UploadAvatarResponse
}