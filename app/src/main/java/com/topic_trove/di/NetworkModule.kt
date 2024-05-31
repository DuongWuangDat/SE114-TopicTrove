package com.topic_trove.di

import com.google.gson.GsonBuilder
import com.topic_trove.data.sharepref.SharePreferenceProvider
import com.topic_trove.ui.core.utils.AppEvent
import com.topic_trove.ui.core.utils.SavedUser
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.greenrobot.eventbus.EventBus
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import javax.net.ssl.HttpsURLConnection

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofitClient(
        sharePreferenceProvider: SharePreferenceProvider,
        authenticator: ClientAuthenticator,
    ): OkHttpClient {
        val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val accessToken =
                    sharePreferenceProvider.getAccessToken()
                val authorization =
                    "Bearer $accessToken"
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", authorization).build()
                chain.proceed(request)
            }
            .addInterceptor(logger)
            .authenticator(authenticator)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        val gson = GsonBuilder()
            .setLenient()
            .create()

        return Retrofit.Builder()
            .baseUrl("https://topictrovebe.onrender.com")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }
}