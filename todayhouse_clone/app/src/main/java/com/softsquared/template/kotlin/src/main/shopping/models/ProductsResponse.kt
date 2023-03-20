package com.softsquared.template.kotlin.src.main.shopping.models

import com.google.gson.annotations.SerializedName
import com.softsquared.template.kotlin.config.BaseResponse

data class ProductsResponse(
//    그러니깐 이게 config에서 baseresponse로 받아오는거니 상관없구만!
//    val code: Int,
//    val isSuccess: Boolean,
//    val message: String,
    @SerializedName("result")val productResult: List<ProductResult>
) : BaseResponse()