package com.softsquared.template.kotlin.src.main.Commnet

import android.util.Log
import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.src.main.Commnet.models.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CommentService(val commentFragmentInterface: CommentFragmentInterface) {

    fun tryGetPheedComment(postId : Int){
        val commentRetrofitInterface = ApplicationClass.sRetrofit.create(CommentRetrofitInterface::class.java)
        commentRetrofitInterface.getPheedComment(postId =  postId).enqueue(object :
            Callback<GetCommentUserResponse> {
            override fun onResponse(call: Call<GetCommentUserResponse>, response: Response<GetCommentUserResponse>,) {
                Log.d("testtest!!!", response.body().toString())
                val commentResult = response.body()?.commentResult
                if(commentResult == null || commentResult.isEmpty()){
                    val emptyCommentResult = GetCommentUserResponse(commentResult = emptyList() )
                    commentFragmentInterface.onGetPheedCommentSuccess(emptyCommentResult)
                }else{
                    commentFragmentInterface.onGetPheedCommentSuccess(response.body() as GetCommentUserResponse)
                }
            }

            override fun onFailure(call: Call<GetCommentUserResponse>, t: Throwable) {
                commentFragmentInterface.onGetPheedCommentFailure(t.message ?: "통신 오류")
            }
        })

    }

    fun tryGetBigComment(parent_id : Int){
        val commentRetrofitInterface = ApplicationClass.sRetrofit.create(CommentRetrofitInterface::class.java)
        commentRetrofitInterface.getBigComment(parent_id = parent_id).enqueue(object :
            Callback<GetBigCommentResponse> {
            override fun onResponse(call: Call<GetBigCommentResponse>, response: Response<GetBigCommentResponse>, ) {
                Log.d("testtest_big",response.message())
                Log.d("testtest_big",response.code().toString())
                Log.d("testtest_big",response.isSuccessful.toString())
                Log.d("testtest_big",response.message())
                if(response.body()?.bigCommentResult == null){
                    val emptyBigCommentResult = GetBigCommentResponse(bigCommentResult = emptyList())
                    commentFragmentInterface.onGetBigCommentSuccess(emptyBigCommentResult)
                }else{
                    commentFragmentInterface.onGetBigCommentSuccess(response.body() as GetBigCommentResponse)
                }
            }

            override fun onFailure(call: Call<GetBigCommentResponse>, t: Throwable) {
                commentFragmentInterface.onGetBigCommentFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryPostComment(postCommentRequest: PostCommentRequest){
        val commentRetrofitInterface = ApplicationClass.sRetrofit.create(CommentRetrofitInterface::class.java)
        commentRetrofitInterface.postComment(postCommentRequest).enqueue(object : Callback<PostCommentResponse>{
            override fun onResponse(
                call: Call<PostCommentResponse>,
                response: Response<PostCommentResponse>,
            ) {
                commentFragmentInterface.onPostCommentSuccess(response.body() as PostCommentResponse)
            }

            override fun onFailure(call: Call<PostCommentResponse>, t: Throwable) {
                commentFragmentInterface.onPostCommentFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryPostCommentLike(comment_id : Int){
        val commentRetrofitInterface = ApplicationClass.sRetrofit.create(CommentRetrofitInterface::class.java)
        commentRetrofitInterface.postCommentLike(comment_id = comment_id).enqueue(object : Callback<PostCommentLikeResponse>{
            override fun onResponse(call: Call<PostCommentLikeResponse>, response: Response<PostCommentLikeResponse>, ) {
                commentFragmentInterface.onPostCommentLikeSuccess(response.body() as PostCommentLikeResponse)
            }

            override fun onFailure(call: Call<PostCommentLikeResponse>, t: Throwable) {
                commentFragmentInterface.onPostCommentLikeFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryPatchCommentLike(like_id : Int, status : Boolean){
        val commentRetrofitInterface = ApplicationClass.sRetrofit.create(CommentRetrofitInterface::class.java)
        commentRetrofitInterface.patchCommentLike(like_id = like_id,status = status).enqueue(object :Callback<PatchCommentLikeResponse>{
            override fun onResponse(
                call: Call<PatchCommentLikeResponse>,
                response: Response<PatchCommentLikeResponse>,
            ) {
                commentFragmentInterface.onPatchCommentLikeSuccess(response.body() as PatchCommentLikeResponse)
            }

            override fun onFailure(call: Call<PatchCommentLikeResponse>, t: Throwable) {
                commentFragmentInterface.onPatchCommentLikeFailure(t.message ?: "통신 오류")
            }
        })
    }


}