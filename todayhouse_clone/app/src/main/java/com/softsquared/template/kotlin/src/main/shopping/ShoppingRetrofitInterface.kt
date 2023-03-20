package com.softsquared.template.kotlin.src.main.shopping

import com.softsquared.template.kotlin.src.main.shopping.Detail.models.*
import com.softsquared.template.kotlin.src.main.shopping.models.ProductsResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ShoppingRetrofitInterface {

    @GET("/products")
    fun getProduct() : Call<ProductsResponse>

    @GET("/products/{productNum}")
    fun getDetail(@Path("productNum") productNum : Int?) : Call<DetailResponse>

    @POST("/carts")
    fun postCart(@Query("userNum") userNum : Int,@Body params: PostCartRequest) : Call<DetailCartResponse>

    @GET("/products/{productNum}/review")
    fun getReview(@Path("productNum") productNum: Int?) : Call<DetailReviewResponse>

    @Multipart
    @POST("/products/{productNum}/review/write")
//    @Header("accept: application/json","content-type: application/json")
    fun postReview(
        @Path("productNum") productNum: Int?,
        //@Part("req") req: DetailReviewWriteBody?,
        @Part("req") req: RequestBody.Companion,
        @Part file: MultipartBody.Part?
    ) : Call<DetailReviewWriteResponse>

}