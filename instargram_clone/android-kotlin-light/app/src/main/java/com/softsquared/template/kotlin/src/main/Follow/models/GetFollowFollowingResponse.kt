package com.softsquared.template.kotlin.src.main.Follow.models

import com.google.gson.annotations.SerializedName
import com.softsquared.template.kotlin.config.BaseResponse

data class GetFollowFollowingResponse(
    @SerializedName("result")val followFollowingResult: FollowFollowingResult
) : BaseResponse()