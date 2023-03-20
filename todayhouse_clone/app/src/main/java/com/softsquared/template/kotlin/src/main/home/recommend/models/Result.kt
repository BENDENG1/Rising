package com.softsquared.template.kotlin.src.main.home.recommend.models

data class Result(
    val contentCate: Int,
    val contentImg: String?,
    val contentNum: Int,
    val contentTitle: String,
    val contents: String,
    val createdAt: Long,
    val form: Int,
    val size: Int,
    val style: Int,
    val userImg: String?,
    val userNickName: String,
    val userNum: Int
)