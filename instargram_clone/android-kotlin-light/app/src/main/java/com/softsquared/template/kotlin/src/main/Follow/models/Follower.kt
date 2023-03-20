package com.softsquared.template.kotlin.src.main.Follow.models

data class Follower(
    val follow_status: Int,
    val name: String,
    val user_id : Int,
    val nickname: String,
    val profile_image_url: String,
    val story_status: Int
)