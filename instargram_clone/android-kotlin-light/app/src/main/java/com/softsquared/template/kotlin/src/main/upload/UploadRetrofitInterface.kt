package com.softsquared.template.kotlin.src.main.upload

import com.softsquared.template.kotlin.src.main.upload.models.PostUploadRequest
import com.softsquared.template.kotlin.src.main.upload.models.PostUploadResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface UploadRetrofitInterface {

    @POST("/app/posts")
    fun postUploadPost(@Body params : PostUploadRequest) : Call<PostUploadResponse>
}