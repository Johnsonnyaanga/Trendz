package com.example.trendz.Repository.MovieRepository

import com.example.trendz.Api.RestService
import com.example.trendz.models.Actors.Actors
import com.example.trendz.models.Genre.GenreResponse
import com.example.trendz.models.People.People
import com.example.trendz.models.PopularMovies.PopularMovieResponse
import com.example.trendz.models.Trending.TrendingResponse
import com.example.trendz.models.UpcomingMovies.UpcomingMovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Inject

class MoviesRepository @Inject constructor (
    val restService: RestService
) {


    suspend fun fetchTrendingMovies(
        mediaType: String,
        timeWindow: String,
        apiKey: String,
        language: String?
    ) = restService.fetchTrendingMovies(mediaType,timeWindow,apiKey,language )

    suspend fun fetchMovieGenres(
        apiKey: String,
        language: String?
    ) = restService.fetchMovieGenres(apiKey, language)

    suspend fun fetchPopularMovies(
        apiKey: String,
        language: String?,
        page: Int = 1
    ) = restService.fetchPopularMovies(apiKey,language,page)

    suspend fun fetchUpcomingMovies(
        apiKey: String,
        language: String?,
        page: Int = 1
    ) = restService.fetchUpcomingMovies(apiKey,language,page)


    suspend fun fetchMovieDetails(
        movieId: Int,
        apiKey: String,
        language: String?
    ) = restService.fetchMovieDetails(movieId,apiKey,language)



    suspend fun fetchPopularPeople(
        apiKey: String,
        language: String?,
        page: Int = 1,
    ) = restService.fetchPopularPeople(apiKey,language,page)


    suspend fun fetchMovieActors(
        movieId: Int,
        apiKey: String,
        language: String?
    ) = restService.fetchMovieActors(movieId,apiKey,language)

}