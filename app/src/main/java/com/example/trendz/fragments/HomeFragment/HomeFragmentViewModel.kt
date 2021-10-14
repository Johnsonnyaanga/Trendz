package com.example.trendz.fragments.HomeFragment

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.lifecycle.*

import com.example.trendz.Repository.MovieRepository.MoviesRepository
import com.example.trendz.di.TrendzApp
import com.example.trendz.models.Trending.TrendingResponse
import com.example.trendz.utils.Constants.API_KEY
import com.example.trendz.utils.Constants.LANG_US
import com.example.trendz.utils.Constants.MEDIA_TYPE_MOVIE
import com.example.trendz.utils.Constants.TIME_WINDOW_WEEK
import com.example.trendz.utils.InternetCheck
import com.example.trendz.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(
    val moviesRepository: MoviesRepository
):ViewModel(){
    val trendMoviesResponse=MutableLiveData<Resource<TrendingResponse>>()
    var trendResponse: TrendingResponse? = null
    var breakingNewsPage = 1

    @Inject
    lateinit var internetCheck:InternetCheck



   init {
       fetchTrendingMovies(MEDIA_TYPE_MOVIE, TIME_WINDOW_WEEK,API_KEY, LANG_US)
       getTrendingMovies(MEDIA_TYPE_MOVIE, TIME_WINDOW_WEEK,API_KEY, LANG_US)
   }

    fun getTrendingMovies( mediaType: String,
                           timeWindow: String,
                           apiKey: String,
                           language: String?) = viewModelScope.launch {
        safeTrendingMoviesCall(mediaType,timeWindow,apiKey,language)
    }

    val _tredingMoviesResponse = MutableLiveData<Response<TrendingResponse>>()
    val _tredingMoviesResponseResource = MutableLiveData<Resource<TrendingResponse>>()
    val tredingMoviesResponse:LiveData<Response<TrendingResponse>> = _tredingMoviesResponse
    val tredingMoviesResponseResource:LiveData<Resource<TrendingResponse>> = _tredingMoviesResponseResource
     fun fetchTrendingMovies(
         mediaType: String,
         timeWindow: String,
        apiKey: String,
        language: String?
    ) = viewModelScope.launch {
         Log.d("viewmodela",moviesRepository.fetchTrendingMovies(mediaType,timeWindow,apiKey,language).toString())
         val res = moviesRepository.fetchTrendingMovies(mediaType,timeWindow,apiKey,language)
         _tredingMoviesResponse.value = res
         val resourceRes = handleTrendingMoviesResponse(res)
         _tredingMoviesResponseResource.value = resourceRes

     }





    fun handleTrendingMoviesResponse(response: Response<TrendingResponse>)
    : Resource<TrendingResponse> {

        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                trendResponse = resultResponse
                return Resource.Success(trendResponse?:resultResponse)
            }
        }
        return Resource.Error(response.message(), null)
    }



     private suspend fun safeTrendingMoviesCall(mediaType: String,
                                                timeWindow: String,
                                                apiKey: String,
                                                language: String?) {

        trendMoviesResponse.postValue(Resource.Loading())
        try {
            if (internetCheck.hasInternetConnection()) {
                val response = moviesRepository.fetchTrendingMovies(mediaType,timeWindow,apiKey,language)
                trendMoviesResponse.postValue(handleTrendingMoviesResponse(response))

            } else {
                trendMoviesResponse.postValue(Resource.Error("No Internet Connection", null))
            }

        } catch (t: Throwable) {
            when (t) {
                is IOException -> trendMoviesResponse.postValue(Resource.Error("NetworkFailure", null))
                else -> trendMoviesResponse.postValue(Resource.Error("Conversion Error", null))
            }

        }

    }












}