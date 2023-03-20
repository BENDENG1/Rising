package com.softsquared.template.kotlin.src.main.Commnet.models

data class PostCommentLikeResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: String
)