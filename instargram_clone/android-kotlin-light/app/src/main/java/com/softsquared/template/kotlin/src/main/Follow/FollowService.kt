package com.softsquared.template.kotlin.src.main.Follow

import android.util.Log
import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.src.main.Follow.models.GetFollowFollowingResponse
import com.softsquared.template.kotlin.src.main.Follow.models.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowService(val followFragmentInterface: FollowFragmentInterface) {

    fun tryGetFollowAnother(user_id: Int){
        val followRetrofitInterface = ApplicationClass.sRetrofit.create(FollowRetrofitInterface::class.java)
        followRetrofitInterface.getFollowAnother(user_id = user_id).enqueue(object :Callback<GetFollowAnotherResponse>{
            override fun onResponse(
                call: Call<GetFollowAnotherResponse>,
                response: Response<GetFollowAnotherResponse>,
            ) {
//                Log.d("testtest",response.body().toString())
//                Log.d("testtest",response.body()?.message.toString())
//                Log.d("testtest",response.body()?.isSuccess.toString())
//                Log.d("testtest",response.body()?.code.toString())
                val anotherResult = response.body()?.followAnotherResult
                if(anotherResult == null || anotherResult.connected_follows.isEmpty() ){
                    val another = anotherResult?.connected_follows?: emptyList()
                    val getFollowAnotherResponse = GetFollowAnotherResponse(FollowAnotherResult(0,another,0,0))
                    followFragmentInterface.onGetFollowAnotherSuccess(getFollowAnotherResponse)
                }else{
                    followFragmentInterface.onGetFollowAnotherSuccess(response.body() as GetFollowAnotherResponse)
                }
            }

            override fun onFailure(call: Call<GetFollowAnotherResponse>, t: Throwable) {
                followFragmentInterface.onGetFollowAnotherFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryGetFollowFollowers(user_id : Int){
        val followRetrofitInterface = ApplicationClass.sRetrofit.create(FollowRetrofitInterface::class.java)
        followRetrofitInterface.getFollowFollowers(user_id = user_id).enqueue(object :Callback<GetFollowFollowersResponse>{
            override fun onResponse(call: Call<GetFollowFollowersResponse>, response: Response<GetFollowFollowersResponse>, ) {
                val followersResult = response.body()?.followFollowerResult
                if(followersResult == null || followersResult.follower.isEmpty()){
                    val follower = followersResult?.follower?: emptyList()
                    val getFollowerAnotherResponse = GetFollowFollowersResponse(FollowFollowerResult(0,0,follower,0))
                    followFragmentInterface.onGetFollowFollowerSuccess(getFollowerAnotherResponse)
                }else{
                    followFragmentInterface.onGetFollowFollowerSuccess(response.body() as GetFollowFollowersResponse)
                }
            }

            override fun onFailure(call: Call<GetFollowFollowersResponse>, t: Throwable) {
                followFragmentInterface.onGetFollowFollwerFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryGetFollowFollowing(user_id : Int){
        val followRetrofitInterface = ApplicationClass.sRetrofit.create(FollowRetrofitInterface::class.java)
        followRetrofitInterface.getFollowFollowing(user_id = user_id).enqueue(object :Callback<GetFollowFollowingResponse>{
            override fun onResponse(call: Call<GetFollowFollowingResponse>, response: Response<GetFollowFollowingResponse>, ) {
                val followingResult = response.body()?.followFollowingResult
                if(followingResult == null || followingResult.followings.isEmpty()){
                    val following = followingResult?.followings?: emptyList()
                    val getFollowingAnotherResponse = GetFollowFollowingResponse(FollowFollowingResult(0,0,0,following))
                    followFragmentInterface.onGetFollowFollowingSuccess(getFollowingAnotherResponse)
                }else{
                    followFragmentInterface.onGetFollowFollowingSuccess(response.body() as GetFollowFollowingResponse)
                }
            }

            override fun onFailure(call: Call<GetFollowFollowingResponse>, t: Throwable) {
                followFragmentInterface.onGetFollowFollowingFailure(t.message ?: "통신 오류")
            }
        })
    }



    fun tryPostDoFollowing(my_id : Int, user_id: Int){
        val followRetrofitInterface = ApplicationClass.sRetrofit.create(FollowRetrofitInterface::class.java)
        followRetrofitInterface.postDoFollowing(user_id = my_id, followUserId = user_id).enqueue(object :Callback<PostDoFollowingResponse>{
            override fun onResponse(call: Call<PostDoFollowingResponse>, response: Response<PostDoFollowingResponse>, ) {
                followFragmentInterface.onPostDoFollowingSuccess(response.body() as PostDoFollowingResponse)
            }

            override fun onFailure(call: Call<PostDoFollowingResponse>, t: Throwable) {
                followFragmentInterface.onPostDoFollowingFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryPatchUnFollowing(my_id: Int, user_id: Int){
        val followRetrofitInterface = ApplicationClass.sRetrofit.create(FollowRetrofitInterface::class.java)
        followRetrofitInterface.postUnFollowing(user_id = my_id, followUserId = user_id).enqueue(object : Callback<PatchUnFollowingResponse>{
            override fun onResponse(call: Call<PatchUnFollowingResponse>, response: Response<PatchUnFollowingResponse>, ) {
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    followFragmentInterface.onPostUnFollowingSuccess(responseBody as PatchUnFollowingResponse)
                } else {
                    val emptyResponse = PatchUnFollowingResponse(UnFollowingResult(0, 0))
                    followFragmentInterface.onPostUnFollowingSuccess(emptyResponse)
                }
            }

            override fun onFailure(call: Call<PatchUnFollowingResponse>, t: Throwable) {
                followFragmentInterface.onPostUnFollowingFailure(t.message ?: "통신오류")
            }
        })
    }
}