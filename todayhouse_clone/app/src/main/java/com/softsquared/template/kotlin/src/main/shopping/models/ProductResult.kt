package com.softsquared.template.kotlin.src.main.shopping.models

import com.google.gson.annotations.SerializedName

data class ProductResult(
    @SerializedName("productCate")val productCate: Int?,
    @SerializedName("productName")val productName: String?,
    @SerializedName("productNum")val productNum: Int?,
    @SerializedName("productPrice")val productPrice: Int?,
    @SerializedName("thumbnail")val thumbnail: String?,
    @SerializedName("reviewCnt")val reviewCnt: String?,
    @SerializedName("reviewAvg")val reviewAvg: Float?,
    @SerializedName("productCom")val productCom: String?,
)