package com.example.trendz.models.PopularMovies

import com.example.trendz.models.Result

data class PopularMovieResponse(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)