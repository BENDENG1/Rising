package com.softsquared.template.kotlin.src.main.Commnet.models

import com.google.gson.annotations.SerializedName
import com.softsquared.template.kotlin.config.BaseResponse

data class GetCommentUserResponse(
    @SerializedName("result")val commentResult: List<CommentResult>
) : BaseResponse()