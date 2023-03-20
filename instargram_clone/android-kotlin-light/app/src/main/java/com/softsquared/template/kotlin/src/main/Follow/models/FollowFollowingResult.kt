package com.softsquared.template.kotlin.src.main.Follow.models

data class FollowFollowingResult(
    val connected_count: Any,
    val follower_count: Int,
    val following_count: Int,
    val followings: List<Following>
)