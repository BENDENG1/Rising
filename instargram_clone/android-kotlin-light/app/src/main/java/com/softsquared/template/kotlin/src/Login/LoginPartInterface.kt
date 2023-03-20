package com.softsquared.template.kotlin.src.Login

import com.softsquared.template.kotlin.src.Login.models.PostJoinResponse
import com.softsquared.template.kotlin.src.Login.models.PostLoginResponse

interface LoginPartInterface {

    fun onPostLoginSuccess(response: PostLoginResponse)

    fun onPostLoginFailure(message : String)

    fun onPostJoinSuccess(response: PostJoinResponse)

    fun onPostJoinFailure(message: String)

}