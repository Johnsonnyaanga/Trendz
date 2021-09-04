package com.example.trendz.models.TopRatedMovies

import com.example.trendz.models.Result

data class TopRatedMoviesResponse(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)