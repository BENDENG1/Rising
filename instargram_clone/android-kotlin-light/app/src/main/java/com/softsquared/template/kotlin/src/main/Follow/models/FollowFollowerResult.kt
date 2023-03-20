package com.softsquared.template.kotlin.src.main.Follow.models

import com.google.gson.annotations.SerializedName

data class FollowFollowerResult(
    val connected_count: Int,
    val follower_count: Int,
    @SerializedName("followers")val follower: List<Follower>,
    val following_count: Int
)