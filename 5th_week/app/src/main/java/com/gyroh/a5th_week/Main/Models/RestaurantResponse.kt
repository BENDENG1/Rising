package com.gyroh.a5th_week.Main.Models

data class RestaurantResponse(
    val currentCount: Int,
    val `data`: List<Data>,
    val matchCount: Int,
    val page: Int,
    val perPage: Int,
    val totalCount: Int
)
