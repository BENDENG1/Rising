package com.softsquared.template.kotlin.src.Login

import com.softsquared.template.kotlin.src.Login.models.PostJoinRequest
import com.softsquared.template.kotlin.src.Login.models.PostJoinResponse
import com.softsquared.template.kotlin.src.Login.models.PostLoginRequest
import com.softsquared.template.kotlin.src.Login.models.PostLoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginRetrofitInterface {

    @POST("/join")
    fun postJoin(@Body params : PostJoinRequest) : Call<PostJoinResponse>

    @POST("/login")
    fun postLogin(@Body params : PostLoginRequest) : Call<PostLoginResponse>
}