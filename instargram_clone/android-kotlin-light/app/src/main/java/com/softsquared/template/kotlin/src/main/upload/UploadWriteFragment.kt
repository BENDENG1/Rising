package com.softsquared.template.kotlin.src.main.upload

import android.os.Bundle
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.config.BaseFragment
import com.softsquared.template.kotlin.databinding.FragmentUploadWriteBinding
import com.softsquared.template.kotlin.src.main.MainActivity
import com.softsquared.template.kotlin.src.main.home.HomeFragment
import com.softsquared.template.kotlin.src.main.upload.models.PostUploadRequest
import com.softsquared.template.kotlin.src.main.upload.models.PostUploadResponse
import com.softsquared.template.kotlin.src.main.upload.models.UploadPhoto
import java.util.ArrayList

class UploadWriteFragment : BaseFragment<FragmentUploadWriteBinding>(FragmentUploadWriteBinding::bind, R.layout.fragment_upload_write),UploadFragmentInterface {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val profile = ApplicationClass.sSharedPreferences.getString("myProfile","")
        val uploadPicture = ApplicationClass.sSharedPreferences.getString("upload_image","")

        val emptyList: List<String> = ArrayList()
        val tagempty : List<String> = ArrayList()
        val photoList : List<UploadPhoto> = listOf(UploadPhoto(uploadPicture.toString(),emptyList))


        Glide.with(this).load(profile).into(binding.civUploadWriteProfile)
        Glide.with(this).load(uploadPicture).into(binding.ivUploadWritePicture)
        binding.ibtnUploadCheck.setOnClickListener {
            val comment = binding.edtUploadWriteComment.text.toString()
            val resquest = PostUploadRequest(1,comment,1,photoList, tagWord = tagempty)
            UploadService(this).tryPostUpload(postUploadRequest = resquest)
        }
    }


    override fun onResume() {
        super.onResume()
        val mainAct = activity as MainActivity
        mainAct.hideNavigationBar(true)
    }

    override fun onPostUploadSuccess(response: PostUploadResponse) {
        parentFragmentManager.beginTransaction().replace(R.id.main_frm, HomeFragment()).commitAllowingStateLoss()
    }

    override fun onPostUploadFailure(message: String) {
        showCustomToast("오류 : $message")
    }
}