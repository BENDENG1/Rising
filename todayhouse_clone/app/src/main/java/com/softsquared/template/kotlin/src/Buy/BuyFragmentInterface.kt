package com.softsquared.template.kotlin.src.Buy

import com.softsquared.template.kotlin.src.Buy.models.CartDeleteResponse
import com.softsquared.template.kotlin.src.Buy.models.CartPatchResponse
import com.softsquared.template.kotlin.src.Buy.models.CartResponse
import com.softsquared.template.kotlin.src.Buy.models.OrderResponse
import com.softsquared.template.kotlin.src.main.shopping.models.ProductsResponse


interface BuyFragmentInterface {

    fun onGetCartsSuccess(response: CartResponse)

    fun onGetCartsFailure(message : String)

    fun onOrderSuccess(response: OrderResponse)

    fun onOrderFailure(message: String)

    fun onGetProductSuccess(response: ProductsResponse)

    fun onGetProductFailure(message : String)

    fun onPatchCartSuccess(response: CartPatchResponse)

    fun onPatchCartFailure(message: String)

    fun onDeleteCartSuccess(response : CartDeleteResponse)

    fun onDeleteCartFailure(message: String)

}