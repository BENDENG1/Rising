package com.softsquared.template.kotlin.src.Login

import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.src.Login.models.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginService(val loginPartInterface: LoginPartInterface){

    fun tryPostLogin(postLoginRequest: PostLoginRequest){
        val loginRetrofitInterface = ApplicationClass.sRetrofit.create(LoginRetrofitInterface::class.java)
        loginRetrofitInterface.postLogin(postLoginRequest).enqueue(object : Callback<PostLoginResponse>{
            override fun onResponse(call: Call<PostLoginResponse>, response: Response<PostLoginResponse>, ) {
                loginPartInterface.onPostLoginSuccess(response.body() as PostLoginResponse)
            }

            override fun onFailure(call: Call<PostLoginResponse>, t: Throwable) {
                loginPartInterface.onPostLoginFailure(t.message ?: "통신오류")
            }
        })
    }

    fun tryPostEmailJoin(postJoinEmailRequest: PostJoinEmailRequest){
        val loginRetrofitInterface = ApplicationClass.sRetrofit.create(LoginRetrofitInterface::class.java)
        loginRetrofitInterface.postEmailJoin(postJoinEmailRequest).enqueue(object :Callback<PostJoinResponse>{
            override fun onResponse(
                call: Call<PostJoinResponse>,
                response: Response<PostJoinResponse>,
            ) {
                loginPartInterface.onPostJoinSuccess(response.body() as PostJoinResponse)
            }

            override fun onFailure(call: Call<PostJoinResponse>, t: Throwable) {
                loginPartInterface.onPostJoinFailure(t.message ?: "통신오류")
            }
        })
    }

    fun tryPostPhoneJoin(postJoinPhoneRequest: PostJoinPhoneRequest){
        val loginRetrofitInterface = ApplicationClass.sRetrofit.create(LoginRetrofitInterface::class.java)
        loginRetrofitInterface.postPhoneJoin(postJoinPhoneRequest).enqueue(object :Callback<PostJoinResponse>{
            override fun onResponse(
                call: Call<PostJoinResponse>,
                response: Response<PostJoinResponse>,
            ) {
                loginPartInterface.onPostJoinSuccess(response.body() as PostJoinResponse)
            }

            override fun onFailure(call: Call<PostJoinResponse>, t: Throwable) {
                loginPartInterface.onPostJoinFailure(t.message ?: "통신오류")
            }
        })
    }
}