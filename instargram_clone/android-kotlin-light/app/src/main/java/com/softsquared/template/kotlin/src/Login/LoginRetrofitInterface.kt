package com.softsquared.template.kotlin.src.Login

import com.softsquared.template.kotlin.src.Login.models.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginRetrofitInterface {

    @POST("/app/users/login")
    fun postLogin(@Body params : PostLoginRequest) : Call<PostLoginResponse>

    @POST("/app/users/email")
    fun postEmailJoin(@Body params : PostJoinEmailRequest) : Call<PostJoinResponse>

    @POST("app/users/phone")
    fun postPhoneJoin(@Body params : PostJoinPhoneRequest) : Call<PostJoinResponse>

}