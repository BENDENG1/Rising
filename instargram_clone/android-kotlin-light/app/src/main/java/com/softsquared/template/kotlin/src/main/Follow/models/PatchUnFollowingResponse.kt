package com.softsquared.template.kotlin.src.main.Follow.models

import com.google.gson.annotations.SerializedName
import com.softsquared.template.kotlin.config.BaseResponse

data class PatchUnFollowingResponse(
    @SerializedName("result")val unFollowingResult: UnFollowingResult
) : BaseResponse()