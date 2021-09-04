package com.example.trendz.di

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.example.trendz.Repository.MovieRepository.MoviesRepository
import com.example.trendz.fragments.HomeFragment.HomeFragmentViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object ViewModelModule {

    @Singleton
    @Provides
    fun provideMoviesViewModel(
        moviesRepository: MoviesRepository
    ):HomeFragmentViewModel{
        return HomeFragmentViewModel(moviesRepository)
    }

}