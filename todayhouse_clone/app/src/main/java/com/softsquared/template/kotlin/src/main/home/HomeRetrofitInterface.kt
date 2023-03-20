package com.softsquared.template.kotlin.src.main.home

import com.softsquared.template.kotlin.config.BaseResponse
import com.softsquared.template.kotlin.src.main.home.models.HomeResponse
import com.softsquared.template.kotlin.src.main.home.recommend.models.RecommendResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface HomeRetrofitInterface {

    @GET("/home")
    fun getHome() : Call<HomeResponse>

    @GET("/contents/{contentNum}")
    fun getRecommend(@Path("contentNum") contentNum : Int) : Call<RecommendResponse>

}