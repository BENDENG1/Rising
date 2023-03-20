package com.softsquared.template.kotlin.src.main.shopping.Detail.models

import okhttp3.MultipartBody
import retrofit2.http.Multipart

data class DetailReviewWriteRequest(
    // val req: DetailReviewWriteBody,
    val req: String,
    val file : MultipartBody.Part?
)
