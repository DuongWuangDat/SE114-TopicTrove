package com.topic_trove.di

import com.topic_trove.data.repositories.RegisterRepository
import com.topic_trove.data.repositories.RegisterRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface RepositoryModule {

    @Binds
    fun bindRegisterRepository(repository: RegisterRepositoryImpl): RegisterRepository
}
