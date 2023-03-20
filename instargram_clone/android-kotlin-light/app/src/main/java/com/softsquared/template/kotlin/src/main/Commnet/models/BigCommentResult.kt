package com.softsquared.template.kotlin.src.main.Commnet.models

import com.google.gson.annotations.SerializedName

data class BigCommentResult(
    val bigCommentCount: Int,
    val comment: String,
    val commentId: Int,
    val createdAt: String,
    val groupId: Int,
    val likeCount: Int,
    @SerializedName("likeOn") val bigCommentLikeOn: BigCommentLikeOn,
    val postId: Int,
    val profileName: String,
    val profilePicture: String,
    val updatedAt: String,
    val userId: Int
)