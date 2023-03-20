package com.softsquared.template.kotlin.src.main.home.models

data class PatchPheedScrapResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: String
)