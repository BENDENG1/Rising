package com.softsquared.template.kotlin.src.main.Follow

import com.softsquared.template.kotlin.src.main.Follow.models.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface FollowRetrofitInterface {

    @GET("/app/follows/connected-follows")
    fun getFollowAnother(@Query("user-id")user_id: Int) : Call<GetFollowAnotherResponse>

    @GET("/app/follows/followers")
    fun getFollowFollowers(@Query("user-id") user_id:Int) : Call<GetFollowFollowersResponse>

    @GET("/app/follows/followings")
    fun getFollowFollowing(@Query("user-id")user_id: Int) : Call<GetFollowFollowingResponse>


    @POST("/app/follows/{user-id}")
    fun postDoFollowing(
        @Path("user-id")user_id: Int,
        @Query("follow-user-id")followUserId : Int) : Call<PostDoFollowingResponse>

    @PATCH("/app/follows/{user-id}")
    fun postUnFollowing(
        @Path("user-id")user_id: Int,
        @Query("follow-user-id")followUserId: Int) : Call<PatchUnFollowingResponse>
}