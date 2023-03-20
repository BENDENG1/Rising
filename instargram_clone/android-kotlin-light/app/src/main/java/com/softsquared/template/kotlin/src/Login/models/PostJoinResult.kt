package com.softsquared.template.kotlin.src.Login.models

data class PostJoinResult(
    val jwt: String,
    val user_id: Int
)