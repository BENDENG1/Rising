package com.softsquared.template.kotlin.src.main.home

import com.softsquared.template.kotlin.src.main.home.models.HomeResponse
import com.softsquared.template.kotlin.src.main.home.recommend.models.RecommendResponse
import retrofit2.Call
import retrofit2.http.GET

interface HomeFragmentInterface {

    fun onGetHomeSuccess(response: HomeResponse)

    fun onGetHomeFailure(message : String)

    fun onGetRecommendSucccess(response: RecommendResponse)

    fun onGetRecommenedFailure(message: String)
}