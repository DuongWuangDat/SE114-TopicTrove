package com.topic_trove.di

import com.topic_trove.ui.core.utils.SavedUser
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CommonModule {
    @Singleton
    @Provides
    fun provideUserData(): SavedUser {
        return SavedUser()
    }
}