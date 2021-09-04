package com.example.trendz.fragments.UpcomingMoviesFragment

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trendz.Repository.MovieRepository.MoviesRepository
import com.example.trendz.models.UpcomingMovies.UpcomingMovieResponse
import com.example.trendz.utils.Constants.API_KEY
import com.example.trendz.utils.Constants.LANG_US
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class UpcomingMoviesViewModel @Inject constructor(
    val moviesRepository: MoviesRepository
): ViewModel() {
    init {
        fetchUpcomingMovies(API_KEY, LANG_US)
    }

    val _upComingMoviesResponse = MutableLiveData<Response<UpcomingMovieResponse>>()
    val upComingMoviesResponse:LiveData<Response<UpcomingMovieResponse>> = _upComingMoviesResponse

    fun fetchUpcomingMovies(
        apiKey: String,
        language: String?,
        page: Int = 1
    ) = viewModelScope.launch {
        Log.d("ppmovie", moviesRepository.fetchUpcomingMovies(apiKey,language,page).toString())

        _upComingMoviesResponse.value = moviesRepository.fetchUpcomingMovies(apiKey,language,page)
    }

}