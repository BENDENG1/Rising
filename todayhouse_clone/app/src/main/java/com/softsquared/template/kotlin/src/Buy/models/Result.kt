package com.softsquared.template.kotlin.src.Buy.models

data class Result(
    val cartList: MutableList<Cart>,
    val totalPrice: Int,
    val userNum: Int
)