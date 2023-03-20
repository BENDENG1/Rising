package com.softsquared.template.kotlin.src.main.shopping

import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.src.main.shopping.Detail.models.*
import com.softsquared.template.kotlin.src.main.shopping.models.ProductsResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ShoppingService(val shoppingFragmentInterface: ShoppingFragmentInterface) {

    fun tryGetProducts(){
        val shoppingRetrofitInterface = ApplicationClass.sRetrofit.create(ShoppingRetrofitInterface::class.java)
        shoppingRetrofitInterface.getProduct().enqueue(object  : Callback<ProductsResponse>{
            override fun onResponse(
                call: Call<ProductsResponse>,
                response: Response<ProductsResponse>
            ) {
                shoppingFragmentInterface.onGetProductSuccess(response.body() as ProductsResponse)
            }

            override fun onFailure(call: Call<ProductsResponse>, t: Throwable) {
                shoppingFragmentInterface.onGetProductFailure(t.message ?:"통신 오류")
            }
        })

    }

    fun tryGetDetail(productNum :Int){
        val shoppingRetrofitInterface = ApplicationClass.sRetrofit.create(ShoppingRetrofitInterface::class.java)
        shoppingRetrofitInterface.getDetail(productNum = productNum).enqueue(object : Callback<DetailResponse>{
            override fun onResponse(
                call: Call<DetailResponse>,
                response: Response<DetailResponse>
            ) {
                shoppingFragmentInterface.onGetDetailSuccess(response.body() as DetailResponse)
            }

            override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                shoppingFragmentInterface.onGetProductFailure(t.message ?:"통신오류")
            }
        })
    }

    fun tryPostCart(userNum : Int, postCartRequest: PostCartRequest){
        val shoppingRetrofitInterface = ApplicationClass.sRetrofit.create(ShoppingRetrofitInterface::class.java)
        shoppingRetrofitInterface.postCart(userNum = userNum,postCartRequest).enqueue(object : Callback<DetailCartResponse>{
            override fun onResponse(call: Call<DetailCartResponse>, response: Response<DetailCartResponse>) {
                shoppingFragmentInterface.onPostCartSuccess(response.body() as DetailCartResponse)
            }

            override fun onFailure(call: Call<DetailCartResponse>, t: Throwable) {
                shoppingFragmentInterface.onPostCartFailure(t.message ?:"통신오류")
            }
        })
    }

    fun tryGetReview(productNum: Int){
        val shoppingRetrofitInterface = ApplicationClass.sRetrofit.create(ShoppingRetrofitInterface::class.java)
        shoppingRetrofitInterface.getReview(productNum = productNum).enqueue(object  : Callback<DetailReviewResponse>{
            override fun onResponse(call: Call<DetailReviewResponse>, response: Response<DetailReviewResponse>) {
                //여기서 왜 터질까..
                shoppingFragmentInterface.onGetReviewSuccess(response.body() as DetailReviewResponse)
            }

            override fun onFailure(call: Call<DetailReviewResponse>, t: Throwable) {
                shoppingFragmentInterface.onGetReviewFailure(t.message ?: "통신오류")
            }
        })
    }

    fun tryPostReivew(
        productNum: Int,
        //detailReviewWriteBody: DetailReviewWriteBody?,
        string: String,
        multipartBody: MultipartBody.Part?
    ){
        val shoppingRetrofitInterface = ApplicationClass.sRetrofit.create(ShoppingRetrofitInterface::class.java)
        shoppingRetrofitInterface.postReview(productNum = productNum, req = RequestBody, file = multipartBody) //detailReviewWriteBody
            .enqueue(object : Callback<DetailReviewWriteResponse>{
            override fun onResponse(call: Call<DetailReviewWriteResponse>, response: Response<DetailReviewWriteResponse>) {
                shoppingFragmentInterface.onPostReviewSuccess(response.body() as DetailReviewWriteResponse)
            }

            override fun onFailure(call: Call<DetailReviewWriteResponse>, t: Throwable) {
                shoppingFragmentInterface.onPostReviewFailure(t.message ?:"통신오류")
            }
        })
    }

}