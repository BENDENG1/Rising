package com.softsquared.template.kotlin.src.Buy.models

import com.softsquared.template.kotlin.config.BaseResponse

data class CartResponse(
    val result: Result
) : BaseResponse()