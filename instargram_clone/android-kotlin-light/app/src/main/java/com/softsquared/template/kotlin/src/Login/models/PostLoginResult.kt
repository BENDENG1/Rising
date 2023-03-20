package com.softsquared.template.kotlin.src.Login.models

data class PostLoginResult(
    val jwt: String?,
    val user_id: Int
)