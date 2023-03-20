package com.softsquared.template.kotlin.src.Buy.models

import com.softsquared.template.kotlin.config.BaseResponse

data class CartPatchResponse(
    val result: String
) : BaseResponse()