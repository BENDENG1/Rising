package com.softsquared.template.kotlin.src.main.profile

import com.softsquared.template.kotlin.src.main.profile.models.GetProfilePostResponse
import com.softsquared.template.kotlin.src.main.profile.models.GetProfileResponse

interface ProfilePartInterface {

    fun onGetProfileSuccess(response : GetProfileResponse)

    fun onGetProfileFailure(message : String)

    fun onGetProfilePostSuccess(response: GetProfilePostResponse)

    fun onGetProfilePostFailure(message : String)
}