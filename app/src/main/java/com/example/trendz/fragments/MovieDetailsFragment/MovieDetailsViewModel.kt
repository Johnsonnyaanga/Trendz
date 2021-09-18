package com.example.trendz.fragments.MovieDetailsFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trendz.Repository.MovieRepository.MoviesRepository
import com.example.trendz.models.Actors.Actors
import com.example.trendz.models.Movie.Movie
import com.example.trendz.models.PopularMovies.PopularMovieResponse
import com.example.trendz.utils.Constants.API_KEY
import com.example.trendz.utils.Constants.LANG_US
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    val moviesRepository: MoviesRepository
) : ViewModel() {


    val _movieDetailsResponse = MutableLiveData<Response<Movie>>()
    val movieDetailsResponse: LiveData<Response<Movie>> = _movieDetailsResponse

    fun fetchMovieDetails(
        movieId: Int,
        apiKey: String,
        language: String?
    ) = viewModelScope.launch {
        _movieDetailsResponse.value = moviesRepository.fetchMovieDetails(movieId,apiKey,language)
    }

    val _movieActorsResponse = MutableLiveData<Response<Actors>>()
    val movieActorsResponse:LiveData<Response<Actors>> = _movieActorsResponse
    fun fetchMovieActors(
        movieId: Int,
        apiKey: String,
        language: String?
    ) = viewModelScope.launch {
        _movieActorsResponse.value = moviesRepository.fetchMovieActors(movieId,apiKey,language)
    }
}