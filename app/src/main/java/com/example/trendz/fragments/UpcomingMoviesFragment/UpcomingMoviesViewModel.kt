package com.example.trendz.fragments.UpcomingMoviesFragment

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trendz.Repository.MovieRepository.MoviesRepository
import com.example.trendz.models.PopularMovies.PopularMovieResponse
import com.example.trendz.models.UpcomingMovies.UpcomingMovieResponse
import com.example.trendz.utils.Constants.API_KEY
import com.example.trendz.utils.Constants.LANG_US
import com.example.trendz.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class UpcomingMoviesViewModel @Inject constructor(
    val moviesRepository: MoviesRepository
): ViewModel() {
    var upComingMovieRes:UpcomingMovieResponse? = null
    init {
        fetchUpcomingMovies(API_KEY, LANG_US)
    }

    val _upComingMoviesResponse = MutableLiveData<Resource<UpcomingMovieResponse>>()
    val upComingMoviesResponse:LiveData<Resource<UpcomingMovieResponse>> = _upComingMoviesResponse

    fun fetchUpcomingMovies(
        apiKey: String,
        language: String?,
        page: Int = 1
    ) = viewModelScope.launch {
        Log.d("ppmovie", moviesRepository.fetchUpcomingMovies(apiKey,language,page).toString())
        val res = moviesRepository.fetchUpcomingMovies(apiKey,language,page)
        _upComingMoviesResponse.value = handleUpcomingMoviesResponse(res)
    }

    private fun handleUpcomingMoviesResponse(response: Response<UpcomingMovieResponse>)
            : Resource<UpcomingMovieResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                upComingMovieRes = resultResponse
                return Resource.Success(upComingMovieRes?:resultResponse)
            }
        }
        return Resource.Error(response.message(), null)
    }






}