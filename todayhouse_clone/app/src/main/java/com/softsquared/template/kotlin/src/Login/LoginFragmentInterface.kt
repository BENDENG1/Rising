package com.softsquared.template.kotlin.src.Login

import com.softsquared.template.kotlin.src.Login.models.PostJoinResponse
import com.softsquared.template.kotlin.src.Login.models.PostLoginResponse

interface LoginFragmentInterface {

    fun onPostJoinSuccess(response: PostJoinResponse)

    fun onPostJoinFailure(message : String)

    fun onPostLoginSuccess(response : PostLoginResponse)

    fun onPostLoginFailure(message : String)

}