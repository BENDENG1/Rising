package com.softsquared.template.kotlin.src.main.Follow.models

data class FollowAnotherResult(
    val connected_count: Int,
    val connected_follows: List<ConnectedFollow>,
    val follower_count: Int,
    val following_count: Int
)