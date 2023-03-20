package com.softsquared.template.kotlin.src.Login.models

data class PostJoinResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: String,
)