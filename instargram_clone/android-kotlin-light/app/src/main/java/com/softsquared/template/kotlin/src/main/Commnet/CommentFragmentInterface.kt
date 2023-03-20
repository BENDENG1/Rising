package com.softsquared.template.kotlin.src.main.Commnet

import com.softsquared.template.kotlin.src.main.Commnet.models.*

interface CommentFragmentInterface {
    fun onGetPheedCommentSuccess(response : GetCommentUserResponse)

    fun onGetPheedCommentFailure(message: String)

    fun onGetBigCommentSuccess(response : GetBigCommentResponse)

    fun onGetBigCommentFailure(message : String)

    fun onPostCommentSuccess(response : PostCommentResponse)

    fun onPostCommentFailure(message: String)

    fun onPostCommentLikeSuccess(response : PostCommentLikeResponse)

    fun onPostCommentLikeFailure(message: String)

    fun onPatchCommentLikeSuccess(response: PatchCommentLikeResponse)

    fun onPatchCommentLikeFailure(message: String)

}