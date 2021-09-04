package com.example.trendz.models.People

data class People(
    val page: Int,
    val results: List<ResultPeople>,
    val total_pages: Int,
    val total_results: Int
)