package com.softsquared.template.kotlin.src.main.home.recommend.models

import com.softsquared.template.kotlin.config.BaseResponse

data class RecommendResponse(
    val result: Result
) : BaseResponse()