package com.example.trendz.fragments.PopularMoviesFragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trendz.Repository.MovieRepository.MoviesRepository
import com.example.trendz.models.PopularMovies.PopularMovieResponse
import com.example.trendz.utils.Constants.API_KEY
import com.example.trendz.utils.Constants.LANG_US
import com.example.trendz.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class PopularViewModel @Inject constructor(
    val moviesRepository: MoviesRepository
):ViewModel() {
    var popularmoviesResponseVar:PopularMovieResponse? = null


    init {
        fetchPopularMovies(API_KEY, LANG_US)
    }

    val _popularMoviesResponse = MutableLiveData<Resource<PopularMovieResponse>>()
    val popularMoviesResponse: LiveData<Resource<PopularMovieResponse>> = _popularMoviesResponse

    fun fetchPopularMovies(
        apiKey: String,
        language: String?,
        page: Int = 1
    ) = viewModelScope.launch {
        /*Log.d("ppmovie",moviesRepository.fetchPopularMovies(apiKey,language,page).toString())
       val res = moviesRepository.fetchPopularMovies(apiKey,language,page)
        _popularMoviesResponse.value = handlePopularMoviesResponse(res)*/
        safePopularMvoiesCall(apiKey, language, page)
    }


    private suspend  fun safePopularMvoiesCall(apiKey: String,
                                               language: String?,
                                               page: Int = 1) = run {
        //_tredingMoviesResponseResource.value = Resource.Loading()
        try {
            Log.d("ppmovie",moviesRepository.fetchPopularMovies(apiKey,language,page).toString())
            val res = moviesRepository.fetchPopularMovies(apiKey,language,page)
            _popularMoviesResponse.value = handlePopularMoviesResponse(res)

        }catch (t:Throwable){
            when(t)  {
                is IOException -> _popularMoviesResponse.value =  Resource.Error("Network Error",null)
                else -> _popularMoviesResponse.postValue(Resource.Error("Conversion Error", null))

            }
        }
    }



    private fun handlePopularMoviesResponse(response: Response<PopularMovieResponse>)
            : Resource<PopularMovieResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                popularmoviesResponseVar= resultResponse
                return Resource.Success(popularmoviesResponseVar?:resultResponse)
            }
        }
        return Resource.Error(response.message(), null)
    }

}