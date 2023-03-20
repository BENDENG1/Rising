package com.softsquared.template.kotlin.src.main.shopping.Detail.models

data class ReviewResult(
    val createdAt: Long,
    val point1: Int,
    val point2: Int,
    val point3: Int,
    val point4: Int,
    val pointAvg: Double,
    val productName: String,
    val reviewContent: String,
    val reviewImg: String?,
    val reviewNum: Int,
    val userNickName: String
)