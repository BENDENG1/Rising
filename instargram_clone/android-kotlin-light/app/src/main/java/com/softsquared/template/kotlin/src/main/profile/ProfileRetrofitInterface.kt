package com.softsquared.template.kotlin.src.main.profile

import com.softsquared.template.kotlin.src.main.profile.models.GetProfilePostResponse
import com.softsquared.template.kotlin.src.main.profile.models.GetProfileResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProfileRetrofitInterface {

    @GET("/app/users/{user_id}")
    fun getProfile(@Path("user_id") user_id : Int) : Call<GetProfileResponse>

    @GET("/app/posts/profiles/user")
    fun getProfilePost(@Query("user-id")user_id: Int) : Call<GetProfilePostResponse>
}