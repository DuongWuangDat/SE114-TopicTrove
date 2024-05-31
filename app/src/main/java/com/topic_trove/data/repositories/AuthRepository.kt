package com.topic_trove.data.repositories

import com.google.gson.GsonBuilder
import com.topic_trove.data.model.EmailRequest
import com.topic_trove.data.model.LoginRequest
import com.topic_trove.data.model.RefreshResponse
import com.topic_trove.data.model.RegisterRequest
import com.topic_trove.data.model.RegisterResponse
import com.topic_trove.data.model.SendEmailResponse
import com.topic_trove.data.model.UploadAvatarResponse
import com.topic_trove.data.service.AuthService
import com.topic_trove.data.sharepref.SharePreferenceProvider
import com.topic_trove.ui.core.utils.AppEvent
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import org.greenrobot.eventbus.EventBus
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import javax.inject.Inject
import javax.net.ssl.HttpsURLConnection

interface AuthRepository {

    suspend fun register(
        name: String,
        phone: String,
        email: String,
        password: String,
        avatar: String,
    ): Result<RegisterResponse>

    suspend fun sendEmail(email: String): Result<SendEmailResponse>

    suspend fun login(email: String, password: String): Result<RegisterResponse>
    suspend fun refresh(): Result<RefreshResponse>

    suspend fun uploadAvatar(file: File): Result<UploadAvatarResponse>
}

class AuthRepositoryImpl @Inject constructor(
    apiClient: Retrofit,
    private val sharePreferenceProvider: SharePreferenceProvider,
) : AuthRepository {

    private val service = apiClient.create(AuthService::class.java)
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

    override suspend fun login(email: String, password: String): Result<RegisterResponse> {
        return try {
            val response = service.login(LoginRequest(email, password))
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun refresh(): Result<RefreshResponse> {
        val gson = GsonBuilder()
            .setLenient()
            .create()

        val client = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val refreshToken =
                    sharePreferenceProvider.get<String>(SharePreferenceProvider.REFRESH_TOKEN)
                val authorization =
                    "Bearer $refreshToken"
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", authorization).build()
                chain.proceed(request).apply {
                    if (code == HttpsURLConnection.HTTP_UNAUTHORIZED) {
                        EventBus.getDefault().post(AppEvent.LogOut)
                    }
                }
            }
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://topictrovebe.onrender.com")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        val authService = retrofit.create(AuthService::class.java)

        return try {
            val response = authService.refresh()
            Result.success(response)
        } catch (e: Exception) {
            sharePreferenceProvider.clearAll()
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