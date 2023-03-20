package com.softsquared.template.kotlin.src.main.search

import com.softsquared.template.kotlin.src.main.search.models.GetSearchIdResponse
import com.softsquared.template.kotlin.src.main.search.models.GetSearchOneResponse
import com.softsquared.template.kotlin.src.main.search.models.GetSearchPostResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SearchRetrofitInterface {
    @GET("/app/posts/recommended")
    fun getSearchPost() : Call<GetSearchPostResponse>

    @GET("/app/users")
    fun getSearchId(@Query("user-keyword") user_keyword : String) : Call<GetSearchIdResponse>

    @GET("/app/posts/{post-id}")
    fun getSearchOne(@Path("post-id") post_id : Int) : Call<GetSearchOneResponse>
}