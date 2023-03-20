package com.softsquared.template.kotlin.src.main.home.models

data class PostPheedScrapResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: String
)