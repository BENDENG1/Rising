package com.softsquared.template.kotlin.src.main.search.models

import com.google.gson.annotations.SerializedName

data class SearchOneResult(
    val commentShowStatus: Int,
    val content: String,
    val createdAt: String,
    val likeCount: Int,
    @SerializedName("likeOn")val SearchOnelikeOn: SearchOneLikeOn,
    val likeShowStatus: Int,
    @SerializedName("photos")val searchOnephotos: List<SearchOnePhoto>,
    val place: Any,
    val postId: Int,
    val profileName: String,
    val profilePicture: String,
    @SerializedName("scrapOn")val searchOneScrapOn: SearchOneScrapOn,
    val tagWord: List<String>,
    val updatedAt: String,
    val userFollowOn: Int,
    val userId: Int,
    val userStoryOn: Int
)

//    SearchOne