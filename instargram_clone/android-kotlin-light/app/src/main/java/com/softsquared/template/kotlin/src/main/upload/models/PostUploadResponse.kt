package com.softsquared.template.kotlin.src.main.upload.models

import com.google.gson.annotations.SerializedName
import com.softsquared.template.kotlin.config.BaseResponse

data class PostUploadResponse(
    @SerializedName("result")val uploadResult: UploadResult
) : BaseResponse()