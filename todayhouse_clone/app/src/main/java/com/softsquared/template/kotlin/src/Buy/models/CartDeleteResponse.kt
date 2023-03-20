package com.softsquared.template.kotlin.src.Buy.models

import com.google.gson.annotations.SerializedName
import com.softsquared.template.kotlin.config.BaseResponse

data class CartDeleteResponse(
    @SerializedName("result") val result: String
) : BaseResponse()