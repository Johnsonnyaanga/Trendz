package com.example.trendz.fragments.PopularMoviesFragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trendz.Repository.MovieRepository.MoviesRepository
import com.example.trendz.models.PopularMovies.PopularMovieResponse
import com.example.trendz.utils.Constants
import com.example.trendz.utils.Constants.API_KEY
import com.example.trendz.utils.Constants.LANG_US
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class PopularViewModel @Inject constructor(
    val moviesRepository: MoviesRepository
):ViewModel() {

    init {
        fetchPopularMovies(API_KEY, LANG_US)
    }

    val _popularMoviesResponse = MutableLiveData<Response<PopularMovieResponse>>()
    val popularMoviesResponse: LiveData<Response<PopularMovieResponse>> = _popularMoviesResponse

    fun fetchPopularMovies(
        apiKey: String,
        language: String?,
        page: Int = 1
    ) = viewModelScope.launch {
        Log.d("ppmovie",moviesRepository.fetchPopularMovies(apiKey,language,page).toString())
        _popularMoviesResponse.value = moviesRepository.fetchPopularMovies(apiKey,language,page)
    }

}