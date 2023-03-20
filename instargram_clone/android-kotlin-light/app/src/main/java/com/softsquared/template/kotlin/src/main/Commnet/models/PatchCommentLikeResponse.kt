package com.softsquared.template.kotlin.src.main.Commnet.models

data class PatchCommentLikeResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: String
)