package com.softsquared.template.kotlin.src.main.shopping.Detail.models

import com.fasterxml.jackson.annotation.JsonPropertyOrder

@JsonPropertyOrder ("userNum","point1","point2","point3","point4","reviewContent")
data class DetailReviewWriteBody(
    val userNum : Int,
    val point1: Int,
    val point2: Int,
    val point3: Int,
    val point4: Int,
    val reviewContent: String
)