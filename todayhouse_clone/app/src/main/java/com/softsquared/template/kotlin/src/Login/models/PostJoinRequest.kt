package com.softsquared.template.kotlin.src.Login.models

data class PostJoinRequest(
    val userEmail: String,
    val userPw: String,
    val userNickName: String,
    val agree1: Int,
    val agree2: Int
)