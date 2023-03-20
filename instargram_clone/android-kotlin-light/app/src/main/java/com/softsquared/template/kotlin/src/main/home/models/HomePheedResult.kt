package com.softsquared.template.kotlin.src.main.home.models

import com.google.gson.annotations.SerializedName

data class HomePheedResult(
    val commentShowStatus: Int,
    val content: String,
    val createdAt: String,
    val likeCount: Int,
    @SerializedName("likeOn")val homePheedLikeOn: HomePheedLikeOn,
    val likeShowStatus: Int,
    @SerializedName("photos") val homePheedPhotos: List<HomePheedPhotos>,
    val place: String,
    val postId: Int,
    val profileName: String,
    val profilePicture: String,
    @SerializedName("scrapOn")val scrapOn: HomePheedScrapOn,
    val tagWord: List<String>,
    val updatedAt: String,
    val userFollowOn: Int,
    val userId: Int,
    val userStoryOn: Int
)