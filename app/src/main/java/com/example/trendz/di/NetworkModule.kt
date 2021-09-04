package com.example.trendz.di

import android.app.Application
import com.example.trendz.Api.RestService
import com.example.trendz.utils.Constants.BASE_URL
import com.example.trendz.utils.InternetCheck
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
    @Module
    object NetworkModule {

        @Singleton
        @Provides
        fun providesHttpLoggingInterceptor(): HttpLoggingInterceptor {
            return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        }

        @Singleton
        @Provides
        fun providesConverterFactory(): Converter.Factory {
            return GsonConverterFactory.create()
        }

        @Singleton
        @Provides
        fun providesOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
            return OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .callTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build()
        }

        @Singleton
        @Provides
        fun providesRetrofit(
            gsonConverterFactory: Converter.Factory,
            okHttpClient: OkHttpClient
        ): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(gsonConverterFactory)
                .build()
        }

        @Singleton
        @Provides
        fun providesApiService(retrofit: Retrofit): RestService {
            return retrofit.create(RestService::class.java)
        }




    }
