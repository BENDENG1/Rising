package com.softsquared.template.kotlin.src.Login

import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.src.Login.models.PostJoinRequest
import com.softsquared.template.kotlin.src.Login.models.PostJoinResponse
import com.softsquared.template.kotlin.src.Login.models.PostLoginRequest
import com.softsquared.template.kotlin.src.Login.models.PostLoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginService(val loginFragmentInterface: LoginFragmentInterface) {

    fun tryPostJoin(postJoinRequest: PostJoinRequest){
        val loginRetrofitInterface = ApplicationClass.sRetrofit.create(LoginRetrofitInterface::class.java)
        loginRetrofitInterface.postJoin(postJoinRequest).enqueue(object : Callback<PostJoinResponse>{
            override fun onResponse(call: Call<PostJoinResponse>, response: Response<PostJoinResponse>) {
                loginFragmentInterface.onPostJoinSuccess(response.body() as PostJoinResponse)
            }

            override fun onFailure(call: Call<PostJoinResponse>, t: Throwable) {
                loginFragmentInterface.onPostJoinFailure(t.message ?:"통신 오류")
            }
        })
    }

    fun tryPostLogin(postLoginRequest: PostLoginRequest){
        val loginRetrofitInterface = ApplicationClass.sRetrofit.create(LoginRetrofitInterface::class.java)
        loginRetrofitInterface.postLogin(postLoginRequest).enqueue(object  : Callback<PostLoginResponse>{
            override fun onResponse(call: Call<PostLoginResponse>, response: Response<PostLoginResponse>) {
                loginFragmentInterface.onPostLoginSuccess(response.body() as PostLoginResponse)
            }

            override fun onFailure(call: Call<PostLoginResponse>, t: Throwable) {
                loginFragmentInterface.onPostLoginFailure(t.message ?:"통신 오류")
            }
        })

    }
}