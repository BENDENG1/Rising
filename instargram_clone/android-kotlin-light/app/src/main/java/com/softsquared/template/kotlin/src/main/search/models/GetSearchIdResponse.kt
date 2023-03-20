package com.softsquared.template.kotlin.src.main.search.models

import com.google.gson.annotations.SerializedName
import com.softsquared.template.kotlin.config.BaseResponse

data class GetSearchIdResponse(
    @SerializedName("result")val searchIdResult: List<SearchIdResult>
) : BaseResponse()