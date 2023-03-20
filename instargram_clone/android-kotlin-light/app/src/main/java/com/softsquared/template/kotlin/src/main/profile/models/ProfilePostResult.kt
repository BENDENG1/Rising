package com.softsquared.template.kotlin.src.main.profile.models

import com.google.gson.annotations.SerializedName

data class ProfilePostResult(
    val commentShowStatus: Int,
    val content: String,
    val createdAt: String,
    val likeCount: Int,
    @SerializedName("likeOn") val likeOn: ProfilePostLikeOn,
    val likeShowStatus: Int,
    @SerializedName("photos")val photos: List<ProfilePostPhoto>,
    val place: String,
    val postId: Int,
    val profileName: String,
    val profilePicture: String,
    @SerializedName("scrapOn")val scrapOn: ProfilePostScrapOn,
    val tagWord: List<String>,
    val updatedAt: String,
    val userFollowOn: Int,
    val userId: Int,
    val userStoryOn: Int
)