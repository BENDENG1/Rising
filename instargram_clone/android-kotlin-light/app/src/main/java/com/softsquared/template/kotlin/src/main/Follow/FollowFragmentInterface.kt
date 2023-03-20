package com.softsquared.template.kotlin.src.main.Follow

import com.softsquared.template.kotlin.src.main.Follow.models.GetFollowAnotherResponse
import com.softsquared.template.kotlin.src.main.Follow.models.GetFollowFollowingResponse
import com.softsquared.template.kotlin.src.main.Follow.models.GetFollowFollowersResponse
import com.softsquared.template.kotlin.src.main.Follow.models.PostDoFollowingResponse
import com.softsquared.template.kotlin.src.main.Follow.models.PatchUnFollowingResponse

interface FollowFragmentInterface {

    fun onGetFollowFollowerSuccess(response: GetFollowFollowersResponse)

    fun onGetFollowFollwerFailure(message: String)

    fun onGetFollowFollowingSuccess(response: GetFollowFollowingResponse)

    fun onGetFollowFollowingFailure(message: String)

    fun onGetFollowAnotherSuccess(response : GetFollowAnotherResponse)

    fun onGetFollowAnotherFailure(message: String)

    fun onPostDoFollowingSuccess(response: PostDoFollowingResponse)

    fun onPostDoFollowingFailure(message: String)

    fun onPostUnFollowingSuccess(response: PatchUnFollowingResponse)

    fun onPostUnFollowingFailure(message: String)
}