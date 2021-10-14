package com.example.trendz.di

import com.example.trendz.utils.InternetCheck
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    @Singleton
    @Provides
    fun proidesApp():TrendzApp {
        return TrendzApp()
    }

    @Singleton
    @Provides
    fun providesInternetChecker(
        app: TrendzApp
    ):InternetCheck{
        return InternetCheck(app)
    }


}