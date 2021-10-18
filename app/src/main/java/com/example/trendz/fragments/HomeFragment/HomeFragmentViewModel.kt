package com.example.trendz.fragments.HomeFragment

import android.util.Log
import androidx.lifecycle.*

import com.example.trendz.Repository.MovieRepository.MoviesRepository
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
    var trendResponse: TrendingResponse? = null
    @Inject
    lateinit var internetCheck:InternetCheck



   init {
       fetchTrendingMovies(MEDIA_TYPE_MOVIE, TIME_WINDOW_WEEK,API_KEY, LANG_US)
   }


    val _tredingMoviesResponseResource:MutableLiveData<Resource<TrendingResponse>> = MutableLiveData()
    val tredingMoviesResponseResource:LiveData<Resource<TrendingResponse>> = _tredingMoviesResponseResource
     fun fetchTrendingMovies(
         mediaType: String,
         timeWindow: String,
        apiKey: String,
        language: String?
    ) = viewModelScope.launch {
            /* Log.d("viewmodela",moviesRepository.fetchTrendingMovies(mediaType,timeWindow,apiKey,language).toString())
             val res = moviesRepository.fetchTrendingMovies(mediaType,timeWindow,apiKey,language)
             val resourceRes = handleTrendingMoviesResponse(res)
             _tredingMoviesResponseResource.value = resourceRes*/
        safeTrendingNewsCall(mediaType, timeWindow, apiKey, language)
     }

    suspend  fun safeTrendingNewsCall(mediaType: String,
                                      timeWindow: String,
                                      apiKey: String,
                                      language: String?) = run {
       //_tredingMoviesResponseResource.value = Resource.Loading()
        try {
            Log.d("viewmodela",moviesRepository.fetchTrendingMovies(mediaType,timeWindow,apiKey,language).toString())
            val res = moviesRepository.fetchTrendingMovies(mediaType,timeWindow,apiKey,language)
            val resourceRes = handleTrendingMoviesResponse(res)
            _tredingMoviesResponseResource.postValue(resourceRes)

        }catch (t:Throwable){
            when(t)  {
                is IOException -> _tredingMoviesResponseResource.value =  Resource.Error("Network Error",null)
                    else -> _tredingMoviesResponseResource.postValue(Resource.Error("Conversion Error", null))

                }
        }
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














}