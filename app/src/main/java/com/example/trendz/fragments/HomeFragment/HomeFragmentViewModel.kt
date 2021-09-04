package com.example.trendz.fragments.HomeFragment

import android.util.Log

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trendz.Repository.MovieRepository.MoviesRepository
import com.example.trendz.models.Trending.TrendingResponse
import com.example.trendz.utils.Constants.API_KEY
import com.example.trendz.utils.Constants.LANG_US
import com.example.trendz.utils.Constants.MEDIA_TYPE_MOVIE
import com.example.trendz.utils.Constants.TIME_WINDOW_WEEK
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(
    val moviesRepository: MoviesRepository
):ViewModel(){
   init {
       fetchTrendingMovies(MEDIA_TYPE_MOVIE, TIME_WINDOW_WEEK,API_KEY, LANG_US)
   }

    val _tredingMoviesResponse = MutableLiveData<Response<TrendingResponse>>()
    val tredingMoviesResponse:LiveData<Response<TrendingResponse>> = _tredingMoviesResponse
     fun fetchTrendingMovies(
         mediaType: String,
         timeWindow: String,
        apiKey: String,
        language: String?
    ) = viewModelScope.launch {
         Log.d("viewmodela",moviesRepository.fetchTrendingMovies(mediaType,timeWindow,apiKey,language).toString())
         _tredingMoviesResponse.value = moviesRepository.fetchTrendingMovies(mediaType,timeWindow,apiKey,language)
     }











}