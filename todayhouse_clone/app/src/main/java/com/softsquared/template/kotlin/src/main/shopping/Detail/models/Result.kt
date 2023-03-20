package com.softsquared.template.kotlin.src.main.shopping.Detail.models

data class Result(
    val product: Product,
    val productImg: List<String>,
    val thumbnails: List<String>
)