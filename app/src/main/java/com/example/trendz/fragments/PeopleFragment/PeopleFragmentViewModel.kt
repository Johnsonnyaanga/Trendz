package com.example.trendz.fragments.PeopleFragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trendz.Repository.MovieRepository.MoviesRepository
import com.example.trendz.models.People.People
import com.example.trendz.utils.Constants.API_KEY
import com.example.trendz.utils.Constants.LANG_US
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class PeopleFragmentViewModel @Inject constructor(
    val repository: MoviesRepository
):ViewModel() {

    init {
        fetchPopularPeople(API_KEY, LANG_US,1)
    }

    val _peopleResponse = MutableLiveData<Response<People>>()
    val  peopleResponse: LiveData<Response<People>> = _peopleResponse
    fun fetchPopularPeople(
        apiKey: String,
        language: String?,
        page: Int = 1,
    ) { viewModelScope.launch {
        Log.d("viewmodelpeople",repository.fetchPopularPeople(API_KEY, LANG_US,1).toString())
        _peopleResponse.value = repository.fetchPopularPeople(apiKey,language,page)
    }}
}