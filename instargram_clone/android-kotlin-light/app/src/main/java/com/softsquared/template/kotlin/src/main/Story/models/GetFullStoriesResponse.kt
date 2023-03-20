package com.softsquared.template.kotlin.src.main.Story.models

import com.google.gson.annotations.SerializedName
import com.softsquared.template.kotlin.config.BaseResponse

data class GetFullStoriesResponse(
    @SerializedName("result")val fullStoriesResult: MutableList<FullStoriesResult>
) : BaseResponse()