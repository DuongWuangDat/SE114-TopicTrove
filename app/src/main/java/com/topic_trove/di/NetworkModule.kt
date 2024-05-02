package com.topic_trove.di

import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofitClient(): OkHttpClient {
        val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                // TODO access token when login
                val authorization =
                    "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiI2NjFkZWQ2MzlhOWVjYzRjMjUyNTc3NGQiLCJ0eXBlIjoicmVmcmVzaCIsImlhdCI6MTcxNDYxNDg0MCwiZXhwIjoxNzE3MjA2ODQwfQ.JFAvJ3PObzeKH-k5O36UEbEqCsdgNNcs0XDNpJYpK0A"
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", authorization).build()

                chain.proceed(request)
            }
            .addInterceptor(logger)
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