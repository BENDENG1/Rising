package com.softsquared.template.kotlin.src.main.upload

import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.src.main.upload.models.PostUploadRequest
import com.softsquared.template.kotlin.src.main.upload.models.PostUploadResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UploadService(val uploadFragmentInterface: UploadFragmentInterface) {

    fun tryPostUpload(postUploadRequest: PostUploadRequest){
        val uploadRetrofitInterface = ApplicationClass.sRetrofit.create(UploadRetrofitInterface::class.java)
        uploadRetrofitInterface.postUploadPost(postUploadRequest).enqueue(object : Callback<PostUploadResponse>{
            override fun onResponse(
                call: Call<PostUploadResponse>,
                response: Response<PostUploadResponse>,
            ) {
                uploadFragmentInterface.onPostUploadSuccess(response.body() as PostUploadResponse)
            }

            override fun onFailure(call: Call<PostUploadResponse>, t: Throwable) {
                uploadFragmentInterface.onPostUploadFailure(t.message ?: "통신 오류")
            }
        })
    }

}