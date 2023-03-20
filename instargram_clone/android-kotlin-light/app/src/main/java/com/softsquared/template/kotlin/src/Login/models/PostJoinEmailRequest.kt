package com.softsquared.template.kotlin.src.Login.models

data class PostJoinEmailRequest(
    val email_address: String,
    val birth_date: String,
    val nickname: String,
    val password: String
)