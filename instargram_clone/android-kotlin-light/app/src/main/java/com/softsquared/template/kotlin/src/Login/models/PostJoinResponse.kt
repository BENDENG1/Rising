package com.softsquared.template.kotlin.src.Login.models

import com.google.gson.annotations.SerializedName
import com.softsquared.template.kotlin.config.BaseResponse

data class PostJoinResponse(
    @SerializedName("result") val postJoinResult:PostJoinResult
) : BaseResponse()