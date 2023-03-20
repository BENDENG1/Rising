package com.softsquared.template.kotlin.src.main.home.models

import com.softsquared.template.kotlin.config.BaseResponse

data class HomeResponse(
    val result: Result
) : BaseResponse()