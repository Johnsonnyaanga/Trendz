package com.example.trendz.Api

import com.example.trendz.models.Actors.Actors
import com.example.trendz.models.Genre.GenreResponse
import com.example.trendz.models.Movie.Genre
import com.example.trendz.models.Movie.Movie
import com.example.trendz.models.People.People
import com.example.trendz.models.PopularMovies.PopularMovieResponse
import com.example.trendz.models.Trending.TrendingResponse
import com.example.trendz.models.UpcomingMovies.UpcomingMovieResponse
import okhttp3.MediaType
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RestService {


    @GET("trending/{media_type}/{time_window}")
    suspend fun fetchTrendingMovies(
        @Path("media_type") mediaType: String = "movies",
        @Path("time_window") timeWindow: String = "week",
        @Query("api_key") apiKey: String,
        @Query("language") language: String?
        ): Response<TrendingResponse>

    @GET("person/popular")
    suspend fun fetchPopularPeople(
        @Query("api_key") apiKey: String,
        @Query("language") language: String?,
        @Query("page") page: Int = 1,
    ): Response<People>

    @GET("genre/movie/list")
    suspend fun fetchMovieGenres(
        @Query("api_key") apiKey: String,
        @Query("language") language: String?
    ): Response<GenreResponse>

    @GET("movie/popular")
    suspend fun fetchPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String?,
        @Query("page") page: Int = 1
    ): Response<PopularMovieResponse>

    @GET("movie/upcoming")
    suspend fun fetchUpcomingMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String?,
        @Query("page") page: Int = 1
    ): Response<UpcomingMovieResponse>

    @GET("movie/{movie_id}")
    suspend fun fetchMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String?
    ): Response<Movie>

    @GET("movie/{movie_id}/credits")
    suspend fun fetchMovieActors(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String?
    ): Response<Actors>




}