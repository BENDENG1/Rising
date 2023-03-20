package com.softsquared.template.kotlin.src.main.home

import android.util.Log
import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.src.main.Commnet.models.GetBigCommentResponse
import com.softsquared.template.kotlin.src.main.Commnet.models.GetCommentUserResponse
import com.softsquared.template.kotlin.src.main.home.models.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeService(val homeFragmentInterface: HomeFragmentInterface) {

    fun tryGetHomePheed(){
        val homeRetrofitInterface = ApplicationClass.sRetrofit.create(HomeRetrofitInterface::class.java)
        homeRetrofitInterface.getHomePheed().enqueue(object : Callback<GetHomePheedResponse>{
            override fun onResponse(call: Call<GetHomePheedResponse>, response: Response<GetHomePheedResponse>, ) {
                Log.d("test","${response.message()},${response.errorBody().toString()}")
                val homePheed = response.body()?.homePheedResult
                if(homePheed == null || homePheed.isEmpty()){
                    val homePheedResult = GetHomePheedResponse(emptyList())
                    homeFragmentInterface.onGetHomePheedSuccess(homePheedResult)
                }else{
                    homeFragmentInterface.onGetHomePheedSuccess(response.body() as GetHomePheedResponse)
                }
            }
            override fun onFailure(call: Call<GetHomePheedResponse>, t: Throwable) {
                homeFragmentInterface.onGetHomePheedFailure(t.message ?: "통신 오류")
            }
        })
    }


    fun tryGetHomeStory(){
        val homeRetrofitInterface = ApplicationClass.sRetrofit.create(HomeRetrofitInterface::class.java)
        homeRetrofitInterface.getHomeStory().enqueue(object :Callback<GetHomeStoryResponse>{
            override fun onResponse(
                call: Call<GetHomeStoryResponse>,
                response: Response<GetHomeStoryResponse>,
            ) {
                homeFragmentInterface.onGetHomeStorySuccess(response.body() as GetHomeStoryResponse)
            }

            override fun onFailure(call: Call<GetHomeStoryResponse>, t: Throwable) {
                homeFragmentInterface.onGetHomeStoryFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryPostPheedLike(post_id : Int){
        val homeRetrofitInterface = ApplicationClass.sRetrofit.create(HomeRetrofitInterface::class.java)
        homeRetrofitInterface.postPheedLike(post_id).enqueue(object :Callback<PostPheedLikeRespose>{
            override fun onResponse(
                call: Call<PostPheedLikeRespose>,
                response: Response<PostPheedLikeRespose>,
            ) {
                homeFragmentInterface.onPostPheedLikeSuccess(response.body() as PostPheedLikeRespose)
            }

            override fun onFailure(call: Call<PostPheedLikeRespose>, t: Throwable) {
                homeFragmentInterface.onPostPheedLikeFailure(t.message ?: "통신오류")
            }
        })
    }

    fun tryPatchPheedLike(like_id : Int, status : Boolean){
        val homeRetrofitInterface = ApplicationClass.sRetrofit.create(HomeRetrofitInterface::class.java)
        homeRetrofitInterface.patchPheedLike(like_id, status).enqueue(object : Callback<PatchPheedLikeResponse>{
            override fun onResponse(
                call: Call<PatchPheedLikeResponse>,
                response: Response<PatchPheedLikeResponse>,
            ) {
                homeFragmentInterface.onPatchPheedLikeSuccess(response.body() as PatchPheedLikeResponse)
            }

            override fun onFailure(call: Call<PatchPheedLikeResponse>, t: Throwable) {
                homeFragmentInterface.onPatchPheedLikeFailure(t.message ?: "통신오류")
            }
        })
    }

    fun tryPostPheedScrap(post_id: Int){
        val homeRetrofitInterface = ApplicationClass.sRetrofit.create(HomeRetrofitInterface::class.java)
        homeRetrofitInterface.postPheedScrap(post_id = post_id).enqueue(object : Callback<PostPheedScrapResponse>{
            override fun onResponse(
                call: Call<PostPheedScrapResponse>,
                response: Response<PostPheedScrapResponse>,
            ) {
                homeFragmentInterface.onPostPheedScrapSuccess(response.body() as PostPheedScrapResponse)
            }

            override fun onFailure(call: Call<PostPheedScrapResponse>, t: Throwable) {
                homeFragmentInterface.onPostPheedScrapFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryPatchPheedScrap(scrap_id : Int, status : Boolean){
        val homeRetrofitInterface = ApplicationClass.sRetrofit.create(HomeRetrofitInterface::class.java)
        homeRetrofitInterface.patchPheedScrap(scrap_id = scrap_id, status = status).enqueue(object :Callback<PatchPheedScrapResponse>{
            override fun onResponse(
                call: Call<PatchPheedScrapResponse>,
                response: Response<PatchPheedScrapResponse>,
            ) {
                homeFragmentInterface.onPatchPheedScrapSuccess(response.body() as PatchPheedScrapResponse)
            }

            override fun onFailure(call: Call<PatchPheedScrapResponse>, t: Throwable) {
                homeFragmentInterface.onPatchPheedScrapFailure(t.message ?: "통신 오류")
            }
        })
    }
}
