package com.example.trendz.models.Trending

import com.example.trendz.models.Result

data class TrendingResponse(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)