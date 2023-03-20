package com.softsquared.template.kotlin.src.Login.models

data class PostJoinPhoneRequest(
    val phone_number: String,
    val birth_date: String,
    val nickname: String,
    val password: String
)