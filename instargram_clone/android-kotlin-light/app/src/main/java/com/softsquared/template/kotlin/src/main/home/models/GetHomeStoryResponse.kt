package com.softsquared.template.kotlin.src.main.home.models

import com.google.gson.annotations.SerializedName
import com.softsquared.template.kotlin.config.BaseResponse

data class GetHomeStoryResponse(
    @SerializedName("result")val homeStoryResult: List<HomeStoryResult>
) : BaseResponse()