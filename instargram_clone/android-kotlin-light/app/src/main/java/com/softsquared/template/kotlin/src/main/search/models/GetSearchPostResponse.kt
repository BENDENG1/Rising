package com.softsquared.template.kotlin.src.main.search.models

import com.google.gson.annotations.SerializedName
import com.softsquared.template.kotlin.config.BaseResponse

data class GetSearchPostResponse(
    @SerializedName("result")val searchPostResult: List<SearchPostResult>
) : BaseResponse()