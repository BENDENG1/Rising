package com.softsquared.template.kotlin.src.Login.models

data class PostLoginRequest(
    val userEmail: String,
    val userPw: String
)