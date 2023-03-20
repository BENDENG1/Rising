package com.softsquared.template.kotlin.src.Buy

import com.softsquared.template.kotlin.src.Buy.models.*
import com.softsquared.template.kotlin.src.main.shopping.models.ProductsResponse
import retrofit2.Call
import retrofit2.http.*

interface BuyRetrofitInterface {


    @GET("/carts")
    fun getCarts(@Query("userNum") userNum: Int) : Call<CartResponse>

    @POST("/carts/orders/{userNum}")
    fun postOrder(@Path("userNum") userNum : Int) : Call<OrderResponse>

    @GET("/products")
    fun getProduct() : Call<ProductsResponse>

    @PATCH("/carts")
    fun patchOrder(@Query("userNum") userNum: Int,@Body params : CartPatchRequest) : Call<CartPatchResponse>

    @DELETE("/carts")
    fun deleteCart(@Query("ordersNum") ordersNum : Int) : Call<CartDeleteResponse>
}