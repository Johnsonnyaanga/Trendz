package com.example.trendz.di

import com.example.trendz.Api.RestService
import com.example.trendz.Repository.MovieRepository.MoviesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {
    @Singleton
    @Provides
    fun providesMovieRepository(
       restService: RestService
    ):MoviesRepository{
     return MoviesRepository(restService)
    }
}