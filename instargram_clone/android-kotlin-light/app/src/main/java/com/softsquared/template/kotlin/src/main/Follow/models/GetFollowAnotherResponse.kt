package com.softsquared.template.kotlin.src.main.Follow.models

import com.google.gson.annotations.SerializedName
import com.softsquared.template.kotlin.config.BaseResponse

data class GetFollowAnotherResponse(
    @SerializedName("result")val followAnotherResult: FollowAnotherResult
) : BaseResponse()