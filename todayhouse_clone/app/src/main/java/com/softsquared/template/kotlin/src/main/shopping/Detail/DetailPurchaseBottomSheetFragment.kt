package com.softsquared.template.kotlin.src.main.shopping.Detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.databinding.FragmentDetailPurchaseBottomsheetdialogBinding
import com.softsquared.template.kotlin.src.Buy.CartFragment
import com.softsquared.template.kotlin.src.main.ItemClickListener
import com.softsquared.template.kotlin.src.main.shopping.Detail.models.*
import com.softsquared.template.kotlin.src.main.shopping.ShoppingFragmentInterface
import com.softsquared.template.kotlin.src.main.shopping.ShoppingProductsAdapter
import com.softsquared.template.kotlin.src.main.shopping.ShoppingService
import com.softsquared.template.kotlin.src.main.shopping.models.ProductResult
import com.softsquared.template.kotlin.src.main.shopping.models.ProductsResponse
import java.text.DecimalFormat

class DetailPurchaseBottomSheetFragment: BottomSheetDialogFragment(),ShoppingFragmentInterface{

    private val binding by lazy { FragmentDetailPurchaseBottomsheetdialogBinding.inflate(layoutInflater) }
    private val productNum by lazy {requireArguments().getInt("productNum") }
    private val productName by lazy {requireArguments().getString("productName").toString() }
    private val productPrice by lazy {requireArguments().getInt("productPrice")}
    val userNum  = ApplicationClass.sSharedPreferences.getInt("userNum",0)

    private var orderCnt = 1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //갯수 카운팅, 액수 포맷 및 표현함수
        countProduct()

        binding.tvDetailPurchaseCart.setOnClickListener {
            ShoppingService(this).tryPostCart(userNum = userNum,PostCartRequest(productNum, orderCnt))
        }
        binding.tvDetailPurchaseNow.setOnClickListener {
            //구매창으로 이동
        }

        binding.btnDetailCartClose.setOnClickListener {
            parentFragmentManager.beginTransaction().remove(this).commit()
        }
        binding.btnDetailCartGo.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.main_frm,CartFragment()).commit()
            parentFragmentManager.beginTransaction().remove(this).commit()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun countProduct(){
        val dec = DecimalFormat("##,###,###")
        with(binding){
            tvDetailPurchaseTitle.text = productName
            tvDetailPurchaseSum.text = dec.format(productPrice)+"원"
            tvDetailPurchaseSum2.text = dec.format(productPrice)+"원"

            ivDetailPurchasePlus.setOnClickListener {
                orderCnt += 1
                tvDetailPurchaseCount.text = orderCnt.toString()
                tvDetailPurchaseSum.text = dec.format(productPrice*orderCnt)+"원"
                tvDetailPurchaseSum2.text = dec.format(productPrice*orderCnt)+"원"
            }
            ivDetailPurchaseMinus.setOnClickListener {
                if(orderCnt > 1){
                    orderCnt -=1
                    tvDetailPurchaseSum.text = dec.format(productPrice*orderCnt)+"원"
                    tvDetailPurchaseSum2.text = dec.format(productPrice*orderCnt)+"원"
                }
                tvDetailPurchaseCount.text = orderCnt.toString()
            }
        }
    }

    override fun onGetProductSuccess(response: ProductsResponse) {
        binding.rvDetailCart.apply {
            adapter = ShoppingProductsAdapter(response.productResult,object :
                ItemClickListener<ProductResult> {
                override fun onItemClick(pos: Int, item: ProductResult) {
                    val productNum : Int? = item.productNum
                    parentFragmentManager.beginTransaction().replace(R.id.main_frm,DetailFragment().apply{
                        arguments = Bundle().apply {
                            productNum?.let { putInt("productNum", productNum) }
                        }
                    }).commit()
                    parentFragmentManager.beginTransaction().remove(this@DetailPurchaseBottomSheetFragment).commit()
                }
            })
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        }
        ShoppingService(this).tryGetDetail(productNum)
    }

    override fun onGetProductFailure(message: String) {
        Toast.makeText(context,"통신실패",Toast.LENGTH_SHORT).show()
    }

    override fun onGetDetailSuccess(response: DetailResponse) {
        Glide.with(this).load(response.result.thumbnails[0]).circleCrop().into(binding.ivDetailCartImage)
    }

    override fun onGetDetailFailure(message: String) {}

    override fun onPostCartSuccess(response: DetailCartResponse) {
        binding.clDetailCartDone.visibility = View.VISIBLE
        binding.clDetailPurchase.visibility = View.GONE
        ShoppingService(this).tryGetProducts()
    }

    override fun onPostCartFailure(message: String) {
        Toast.makeText(activity,"통신 오류",Toast.LENGTH_SHORT).show()
    }

    override fun onGetReviewSuccess(response: DetailReviewResponse) {}

    override fun onGetReviewFailure(message: String) {}

    override fun onPostReviewSuccess(response: DetailReviewWriteResponse) {}

    override fun onPostReviewFailure(message: String) {}
}