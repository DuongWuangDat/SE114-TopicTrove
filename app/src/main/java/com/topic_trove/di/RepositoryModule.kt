package com.topic_trove.di

import com.topic_trove.data.repositories.PostRepository
import com.topic_trove.data.repositories.PostRepositoryImpl
import com.topic_trove.data.repositories.AuthRepository
import com.topic_trove.data.repositories.AuthRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface RepositoryModule {

    @Binds
    fun bindRegisterRepository(repository: AuthRepositoryImpl): AuthRepository

    @Binds
    fun bindPostRepository(repository: PostRepositoryImpl): PostRepository
}
