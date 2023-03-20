package com.softsquared.template.kotlin.src.main.upload

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.config.BaseFragment
import com.softsquared.template.kotlin.databinding.FragmentUploadBinding
import com.softsquared.template.kotlin.databinding.UploadImageListBinding
import com.softsquared.template.kotlin.src.main.upload.models.PostUploadResponse
import com.softsquared.template.kotlin.src.main.upload.models.UploadPhoto
import com.softsquared.template.kotlin.util.GridSpacingItemDecoration
import java.util.*

class UploadFragment : BaseFragment<FragmentUploadBinding>(FragmentUploadBinding::bind, R.layout.fragment_upload),UploadFragmentInterface{

    //서버에서 multipart부분을 불가능을 못한다 판단하여 string으로 임시대체
    private var image1 : String = "https://trudylin.s3.ap-northeast-2.amazonaws.com/postPhoto/1.jpg"
    private var image2  : String= "https://trudylin.s3.ap-northeast-2.amazonaws.com/postPhoto/2.png"
    private var image3  : String= "https://trudylin.s3.ap-northeast-2.amazonaws.com/postPhoto/3.png"

    private var clicked :Int = 0
    private var pictureList = mutableListOf<UploadPhoto>()
    var emptyList: List<String> = ArrayList()

    private var testImageList = listOf<UploadPhoto>(
        UploadPhoto(image1,emptyList),UploadPhoto(image2,emptyList),UploadPhoto(image3,emptyList),
        UploadPhoto(image1,emptyList),UploadPhoto(image1,emptyList),UploadPhoto(image2,emptyList),
        UploadPhoto(image3,emptyList),UploadPhoto(image2,emptyList),UploadPhoto(image1,emptyList),
        UploadPhoto(image1,emptyList),UploadPhoto(image2,emptyList),UploadPhoto(image3,emptyList),
        UploadPhoto(image1,emptyList),UploadPhoto(image1,emptyList),UploadPhoto(image2,emptyList),
        UploadPhoto(image3,emptyList),UploadPhoto(image2,emptyList),UploadPhoto(image1,emptyList),
        UploadPhoto(image1,emptyList),UploadPhoto(image2,emptyList),UploadPhoto(image3,emptyList),
        UploadPhoto(image1,emptyList),UploadPhoto(image1,emptyList),UploadPhoto(image2,emptyList),
        UploadPhoto(image3,emptyList),UploadPhoto(image2,emptyList),UploadPhoto(image1,emptyList)
    )

    @SuppressLint("CommitPrefEdits")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        uploadImageRecyclerView()

//        Glide.with(requireContext()).load(testImageList[1]).into(binding.ivUploadPicture)

        binding.ibtnUploadNext.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.main_frm,UploadWriteFragment()).addToBackStack("null").commit()
        }
    }


    private fun uploadImageRecyclerView(){

        binding.rvUploadImage.apply {
            setHasFixedSize(true)
            adapter = UploadImageAdapter(testImageList,object : UploadImageAdapter.UploadImageClickListener<UploadPhoto>{
                override fun onClickListener(view: UploadImageListBinding, pos: Int) {
                    Glide.with(requireContext()).load(testImageList[pos].photoUrl).into(binding.ivUploadPicture)
                    clicked += 1
                    view.tvUploadNumber.text = "$clicked"
                    view.tvUploadNumber.background = ContextCompat.getDrawable(context, R.drawable.upload_circle_skyblue)
                    ApplicationClass.sSharedPreferences.edit().putString("upload_image",testImageList[pos].photoUrl).apply()
                }
            })
            layoutManager = GridLayoutManager(context,4)
            addItemDecoration(GridSpacingItemDecoration(5,4))
        }
    }



    override fun onPostUploadSuccess(response: PostUploadResponse) {
    }

    override fun onPostUploadFailure(message: String) {
    }
}