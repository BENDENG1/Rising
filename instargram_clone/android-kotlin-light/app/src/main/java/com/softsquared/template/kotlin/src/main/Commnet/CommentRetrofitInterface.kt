package com.softsquared.template.kotlin.src.main.Commnet

import com.softsquared.template.kotlin.src.main.Commnet.models.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface CommentRetrofitInterface {

    @GET("/app/posts/comments/{post-id}")
    fun getPheedComment(@Path("post-id")postId : Int) : Call<GetCommentUserResponse>

    @GET("/app/posts/comments/big-comment")
    fun getBigComment(@Query("parent-id")parent_id : Int) : Call<GetBigCommentResponse>

    @POST("/app/posts/comments")
    fun postComment(@Body params : PostCommentRequest) : Call<PostCommentResponse>

    @POST("/app/posts/comments/like-status/{comment-id}")
    fun postCommentLike(@Path("comment-id")comment_id : Int) : Call<PostCommentLikeResponse>

    @PATCH("/app/posts/comments/like-status/{like-id}/{status}")
    fun patchCommentLike(
        @Path("like-id") like_id : Int,
        @Path("status") status :Boolean
    ) : Call<PatchCommentLikeResponse>

}