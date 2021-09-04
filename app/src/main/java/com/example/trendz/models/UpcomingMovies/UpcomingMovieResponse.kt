package com.example.trendz.models.UpcomingMovies

import com.example.trendz.models.Result

data class UpcomingMovieResponse(
    val dates: Dates,
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)