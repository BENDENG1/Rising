package com.softsquared.template.kotlin.src.main.Commnet.models

data class PostCommentRequest(
    val postId: Int,
    val groupId: Int?,
    val comment: String,
)