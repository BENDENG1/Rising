package com.softsquared.template.kotlin.src.main.home

import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.src.main.home.models.HomeResponse
import com.softsquared.template.kotlin.src.main.home.recommend.models.RecommendResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create


class HomeService(val homeFragmentInterface: HomeFragmentInterface){

    fun tryGetHome(){
        val homeRetrofitInterface = ApplicationClass.sRetrofit.create(HomeRetrofitInterface::class.java)
        homeRetrofitInterface.getHome().enqueue(object : Callback<HomeResponse>{
            override fun onResponse(call: Call<HomeResponse>, response: Response<HomeResponse>) {
                homeFragmentInterface.onGetHomeSuccess(response.body() as HomeResponse)
            }

            override fun onFailure(call: Call<HomeResponse>, t: Throwable) {
                homeFragmentInterface.onGetHomeFailure(t.message ?: "통신오류")
            }
        })
    }

    fun tryGetRecommend(contentNum : Int){
        val homerRetrofitInterface = ApplicationClass.sRetrofit.create(HomeRetrofitInterface::class.java)
        homerRetrofitInterface.getRecommend(contentNum).enqueue(object :Callback<RecommendResponse>{
            override fun onResponse(call: Call<RecommendResponse>, response: Response<RecommendResponse>) {
                homeFragmentInterface.onGetRecommendSucccess(response.body() as RecommendResponse)
            }

            override fun onFailure(call: Call<RecommendResponse>, t: Throwable) {
                homeFragmentInterface.onGetRecommenedFailure(t.message ?: "통신오류")
            }
        })
    }

}