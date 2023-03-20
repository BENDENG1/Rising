package com.softsquared.template.kotlin.src.main.search.models

data class SearchIdResult(
    val connected_count: Int,
    val connected_friend_nickname: String,
    val name: String,
    val nickname: String,
    val profile_image_url: String,
    val user_id: Int,
    val story_status : Int
)