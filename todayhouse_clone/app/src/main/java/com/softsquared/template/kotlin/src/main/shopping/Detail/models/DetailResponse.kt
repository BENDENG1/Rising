package com.softsquared.template.kotlin.src.main.shopping.Detail.models

import com.google.gson.annotations.SerializedName
import com.softsquared.template.kotlin.config.BaseResponse

data class DetailResponse(
    @SerializedName("result")val result: Result
) : BaseResponse()