package com.softsquared.template.kotlin.src.main.shopping.Detail.models

data class Product(
    val productCate: Int,
    val productCom: String,
    val productInfo: String,
    val productName: String,
    val productNum: Int,
    val productPrice: Int,
    val reviewAvg: Double,
    val reviewCnt: Int
)