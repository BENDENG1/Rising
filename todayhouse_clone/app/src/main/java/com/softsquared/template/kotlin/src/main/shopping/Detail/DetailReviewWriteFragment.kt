package com.softsquared.template.kotlin.src.main.shopping.Detail

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.fasterxml.jackson.databind.ObjectMapper
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.BaseFragment
import com.softsquared.template.kotlin.databinding.FragmentDetailReivewWriteBinding
import com.softsquared.template.kotlin.src.Buy.BuyFragmentInterface
import com.softsquared.template.kotlin.src.Buy.models.CartDeleteResponse
import com.softsquared.template.kotlin.src.Buy.models.CartPatchResponse
import com.softsquared.template.kotlin.src.Buy.models.CartResponse
import com.softsquared.template.kotlin.src.Buy.models.OrderResponse
import com.softsquared.template.kotlin.src.main.MainActivity
import com.softsquared.template.kotlin.src.main.shopping.Detail.models.*
import com.softsquared.template.kotlin.src.main.shopping.ShoppingFragmentInterface
import com.softsquared.template.kotlin.src.main.shopping.ShoppingService
import com.softsquared.template.kotlin.src.main.shopping.models.ProductsResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class DetailReviewWriteFragment : BaseFragment<FragmentDetailReivewWriteBinding>(FragmentDetailReivewWriteBinding::bind , R.layout.fragment_detail_reivew_write)
    ,ShoppingFragmentInterface,BuyFragmentInterface{

    private val productNum by lazy {requireArguments().getInt("productNum")  }
    private  var reviewImage : MultipartBody.Part? = null
    private var point1 = 0
    private var point2 = 0
    private var point3 = 0
    private var point4 = 0
    private lateinit var reviewContent : String


    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
        val imagePath = result.data!!.data
        val file = File(absolutelyPath(imagePath, requireContext()))
        val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
        val body = MultipartBody.Part.createFormData("proFile", file.name, requestFile)
        Log.d("----",file.name)
        binding.ibtnReviewWriteUploadImage.setImageURI(imagePath)

        reviewImage = body
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //대충 이런식으로 진행했으니 참조바람 (여기꺼 아님)
//        binding.tvDetailPurchaseCart.setOnClickListener {
//            ShoppingService(this).tryPostCart(PostCartRequest(productNum, orderCnt))
//        }

        //사진을 눌렀을때 사진에 대해서 권한을 요청하고 리뷰 사진을 선택하는 절차
        binding.ibtnReviewWriteUploadImage.setOnClickListener {
            checkPersmission()
        }

        binding.btnReviewWriteSubmit.setOnClickListener{
            point1 = binding.rbReviewWritePoint1.rating.toInt()
            point2 = binding.rbReviewWritePoint2.rating.toInt()
            point3 = binding.rbReviewWritePoint3.rating.toInt()
            point4 = binding.rbReviewWritePoint4.rating.toInt()
            reviewContent = binding.edtReviewWriteContent.text.toString()



            val content = DetailReviewWriteBody(14,point1,point2,point3,point4,reviewContent)
            val result = ObjectMapper().writeValueAsString(content)

            Log.d("----","$content")
            Log.d("----", result)

            ShoppingService(this).tryPostReivew(productNum,result,reviewImage)
        }
    }

    override fun onResume() {
        super.onResume()
        val mainAct = activity as MainActivity
        mainAct.bottomNavigationHide(true)
    }

    fun checkPersmission(){
        val permission = ContextCompat.checkSelfPermission(requireContext(),android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if(permission == PackageManager.PERMISSION_GRANTED){
            getProFileImage()
        }else{
            requestPermission()
        }
    }


    private fun requestPermission() {
        ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),99)

    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            99 -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    getProFileImage()
                else
                    Log.d("storage","success")
            }
        }
    }

    @SuppressLint("IntentReset")
    fun getProFileImage(){
        Log.d("-----","사진변경 호출")
        val chooserIntent = Intent(Intent.ACTION_CHOOSER)
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        chooserIntent.putExtra(Intent.EXTRA_INTENT, intent)
        chooserIntent.putExtra(Intent.EXTRA_TITLE,"사용할 앱을 선택해주세요.")
        launcher.launch(chooserIntent)
    }

    // 절대경로 변환
    @SuppressLint("Recycle")
    fun absolutelyPath(path: Uri?, context : Context): String {
        val proj: Array<String> = arrayOf(MediaStore.Images.Media.DATA)
        val c: Cursor? = context.contentResolver.query(path!!, proj, null, null, null)
        val index = c?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        c?.moveToFirst()

        val result = c?.getString(index!!)

        return result!!
    }





    override fun onGetCartsSuccess(response: CartResponse) {}
    override fun onGetCartsFailure(message: String) {}
    override fun onOrderSuccess(response: OrderResponse) {}
    override fun onOrderFailure(message: String) {}
    override fun onGetProductSuccess(response: ProductsResponse) {}
    override fun onGetProductFailure(message: String) {}
    override fun onGetDetailSuccess(response: DetailResponse) {}
    override fun onGetDetailFailure(message: String) {}
    override fun onPostCartSuccess(response: DetailCartResponse) {}
    override fun onPostCartFailure(message: String) {}
    override fun onGetReviewSuccess(response: DetailReviewResponse) {}
    override fun onGetReviewFailure(message: String) {}
    override fun onPostReviewSuccess(response: DetailReviewWriteResponse) {
        Log.d("reviewtest", response.code.toString())
        parentFragmentManager.beginTransaction().remove(this).commit()
    }

    override fun onPostReviewFailure(message: String) {
        showCustomToast("오류 : $message")
    }

    override fun onPatchCartSuccess(response: CartPatchResponse) {}
    override fun onPatchCartFailure(message: String) {}
    override fun onDeleteCartSuccess(response: CartDeleteResponse) {
    }

    override fun onDeleteCartFailure(message: String) {
    }
}