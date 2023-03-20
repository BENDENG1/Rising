package com.softsquared.template.kotlin.src.Buy.models

data class Cart(
    var orderCnt: Int,
    val ordersNum: Int,
    val price: Int,
    val productName: String,
    val productNum: Int,
    val thumbnail: String,
    val productCom : String
)