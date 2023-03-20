package com.softsquared.template.kotlin.src.main.shopping

import com.softsquared.template.kotlin.src.main.shopping.Detail.models.DetailCartResponse
import com.softsquared.template.kotlin.src.main.shopping.Detail.models.DetailResponse
import com.softsquared.template.kotlin.src.main.shopping.Detail.models.DetailReviewResponse
import com.softsquared.template.kotlin.src.main.shopping.Detail.models.DetailReviewWriteResponse
import com.softsquared.template.kotlin.src.main.shopping.models.ProductsResponse

interface ShoppingFragmentInterface {

    fun onGetProductSuccess(response: ProductsResponse)

    fun onGetProductFailure(message : String)

    fun onGetDetailSuccess(response: DetailResponse)

    fun onGetDetailFailure(message : String)

    fun onPostCartSuccess(response:DetailCartResponse)

    fun onPostCartFailure(message: String)

    fun onGetReviewSuccess(response: DetailReviewResponse)

    fun onGetReviewFailure(message :String)

    fun onPostReviewSuccess(response: DetailReviewWriteResponse)

    fun onPostReviewFailure(message: String)


}