package com.softsquared.template.kotlin.src.main.home


import com.softsquared.template.kotlin.src.main.home.models.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query


interface HomeRetrofitInterface {

    @GET("/app/posts/followings")
    fun getHomePheed() : Call<GetHomePheedResponse>

    @GET("/app/stories/followings")
    fun getHomeStory() : Call<GetHomeStoryResponse>

    @POST("/app/posts/likes/{post-id}")
    fun postPheedLike(@Path("post-id")post_id : Int) : Call<PostPheedLikeRespose>

    @PATCH("/app/posts/likes/{like-id}/{status}")
    fun patchPheedLike(
        @Path("like-id")like_id : Int,
        @Path("status")status : Boolean
    ) : Call<PatchPheedLikeResponse>

    @POST("/app/posts/scraped/{post-id}")
    fun postPheedScrap(
        @Path("post-id")post_id: Int,
    ) : Call<PostPheedScrapResponse>

    @PATCH("/app/posts/scraped/{scrap-id}/{status}")
    fun patchPheedScrap(
        @Path("scrap-id")scrap_id : Int,
        @Path("status")status: Boolean
    ) : Call<PatchPheedScrapResponse>
}
