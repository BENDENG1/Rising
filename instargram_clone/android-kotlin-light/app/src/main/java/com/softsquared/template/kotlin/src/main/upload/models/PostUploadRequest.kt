package com.softsquared.template.kotlin.src.main.upload.models

import com.google.gson.annotations.SerializedName

data class PostUploadRequest(
    val commentShowStatus: Int,
    val content: String?,
    val likeShowStatus: Int,
    @SerializedName("photos")val uploadPhotos: List<UploadPhoto>,
    val tagWord: List<String>
)