package com.softsquared.template.kotlin.src.main.upload

import com.softsquared.template.kotlin.src.main.upload.models.PostUploadResponse

interface UploadFragmentInterface {

    fun onPostUploadSuccess(response : PostUploadResponse)

    fun onPostUploadFailure(message : String)

}