package com.example.trendz.di

import com.example.trendz.MainActivity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object ActivitiesModule {

    @Singleton
    @Provides
    fun provideMainActivity():MainActivity{
        return MainActivity()
    }
}