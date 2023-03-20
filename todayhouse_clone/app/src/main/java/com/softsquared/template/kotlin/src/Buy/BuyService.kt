package com.softsquared.template.kotlin.src.Buy

import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.src.Buy.models.*
import com.softsquared.template.kotlin.src.main.shopping.models.ProductsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BuyService (val buyFragmentInterface: BuyFragmentInterface){

    fun tryGetCarts(userNum: Int){
        val buyRetrofitInterface = ApplicationClass.sRetrofit.create(BuyRetrofitInterface::class.java)
        buyRetrofitInterface.getCarts(userNum = userNum).enqueue(object : Callback<CartResponse>{
            override fun onResponse(call: Call<CartResponse>, response: Response<CartResponse>) {
                buyFragmentInterface.onGetCartsSuccess(response.body() as CartResponse)
            }

            override fun onFailure(call: Call<CartResponse>, t: Throwable) {
                buyFragmentInterface.onGetCartsFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryPostPurchase(userNum : Int){
        val buyRetrofitInterface = ApplicationClass.sRetrofit.create(BuyRetrofitInterface::class.java)
        buyRetrofitInterface.postOrder(userNum = userNum).enqueue(object :Callback<OrderResponse>{
            override fun onResponse(call: Call<OrderResponse>, response: Response<OrderResponse>) {
                buyFragmentInterface.onOrderSuccess(response.body() as OrderResponse)
            }

            override fun onFailure(call: Call<OrderResponse>, t: Throwable) {
                buyFragmentInterface.onOrderFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryGetProducts(){
        val buyRetrofitInterface = ApplicationClass.sRetrofit.create(BuyRetrofitInterface::class.java)
        buyRetrofitInterface.getProduct().enqueue(object  : Callback<ProductsResponse>{
            override fun onResponse(
                call: Call<ProductsResponse>,
                response: Response<ProductsResponse>
            ) {
                buyFragmentInterface.onGetProductSuccess(response.body() as ProductsResponse)
            }

            override fun onFailure(call: Call<ProductsResponse>, t: Throwable) {
               buyFragmentInterface.onGetProductFailure(t.message ?:"통신 오류")
            }
        })

    }

    fun tryPatchCarts(userNum: Int, cartPatchRequest: CartPatchRequest){
        val buyRetrofitInterface = ApplicationClass.sRetrofit.create(BuyRetrofitInterface::class.java)
        buyRetrofitInterface.patchOrder(userNum = userNum, cartPatchRequest).enqueue(object  : Callback<CartPatchResponse>{
            override fun onResponse(call: Call<CartPatchResponse>, response: Response<CartPatchResponse>,
            ) {
                buyFragmentInterface.onPatchCartSuccess(response.body() as CartPatchResponse)
            }

            override fun onFailure(call: Call<CartPatchResponse>, t: Throwable) {
                buyFragmentInterface.onPatchCartFailure(t.message ?: "통신오류")
            }
        })
    }

    fun tryDeleteCarts(ordersNum : Int){
        val buyRetrofitInterface = ApplicationClass.sRetrofit.create(BuyRetrofitInterface::class.java)
        buyRetrofitInterface.deleteCart(ordersNum = ordersNum).enqueue(object  : Callback<CartDeleteResponse>{
            override fun onResponse(
                call: Call<CartDeleteResponse>,
                response: Response<CartDeleteResponse>
            ) {
                buyFragmentInterface.onDeleteCartSuccess(response.body() as CartDeleteResponse)
            }

            override fun onFailure(call: Call<CartDeleteResponse>, t: Throwable) {
                buyFragmentInterface.onDeleteCartFailure(t.message ?: "통신오류")
            }
        })
    }

}