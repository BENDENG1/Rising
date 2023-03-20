package com.softsquared.template.kotlin.src.main.home

import com.softsquared.template.kotlin.src.main.Commnet.models.GetBigCommentResponse
import com.softsquared.template.kotlin.src.main.Commnet.models.GetCommentUserResponse
import com.softsquared.template.kotlin.src.main.home.models.*

interface HomeFragmentInterface {

    fun onGetHomePheedSuccess(response : GetHomePheedResponse)

    fun onGetHomePheedFailure(message : String)

    fun onGetHomeStorySuccess(response : GetHomeStoryResponse)

    fun onGetHomeStoryFailure(message: String)

    fun onPostPheedLikeSuccess(response : PostPheedLikeRespose)

    fun onPostPheedLikeFailure(message: String)

    fun onPatchPheedLikeSuccess(response: PatchPheedLikeResponse)

    fun onPatchPheedLikeFailure(message: String)

    fun onPostPheedScrapSuccess(response : PostPheedScrapResponse)

    fun onPostPheedScrapFailure(message: String)

    fun onPatchPheedScrapSuccess(response: PatchPheedScrapResponse)

    fun onPatchPheedScrapFailure(message: String)
}