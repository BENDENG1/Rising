package com.softsquared.template.kotlin.src.main.profile

import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.src.main.profile.models.GetProfilePostResponse
import com.softsquared.template.kotlin.src.main.profile.models.GetProfileResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class ProfileService(val profilePartInterface: ProfilePartInterface) {

    fun tryGetProfile(user_id : Int){
        val profileRetrofitInterface = ApplicationClass.sRetrofit.create(ProfileRetrofitInterface::class.java)
        profileRetrofitInterface.getProfile(user_id = user_id).enqueue(object : Callback<GetProfileResponse>{
            override fun onResponse(call: Call<GetProfileResponse>, response: Response<GetProfileResponse>) {
                profilePartInterface.onGetProfileSuccess(response.body() as GetProfileResponse)
            }

            override fun onFailure(call: Call<GetProfileResponse>, t: Throwable) {
                profilePartInterface.onGetProfileFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryGetProfilePost(user_id: Int){
        val profileRetrofitInterface = ApplicationClass.sRetrofit.create(ProfileRetrofitInterface::class.java)
        profileRetrofitInterface.getProfilePost(user_id = user_id).enqueue(object :Callback<GetProfilePostResponse>{
            override fun onResponse(
                call: Call<GetProfilePostResponse>,
                response: Response<GetProfilePostResponse>,
            ) {
                profilePartInterface.onGetProfilePostSuccess(response.body() as GetProfilePostResponse)
            }

            override fun onFailure(call: Call<GetProfilePostResponse>, t: Throwable) {
                profilePartInterface.onGetProfilePostFailure(t.message ?: "통신 오류")
            }
        })
    }
}