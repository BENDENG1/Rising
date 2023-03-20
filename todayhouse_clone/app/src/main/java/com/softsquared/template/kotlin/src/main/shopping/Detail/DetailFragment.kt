package com.softsquared.template.kotlin.src.main.shopping.Detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.ScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.BaseFragment
import com.softsquared.template.kotlin.databinding.FragmentDetailBinding
import com.softsquared.template.kotlin.src.main.ItemClickListener
import com.softsquared.template.kotlin.src.main.MainActivity
import com.softsquared.template.kotlin.src.main.shopping.Detail.models.*
import com.softsquared.template.kotlin.src.main.shopping.ShoppingFragmentInterface
import com.softsquared.template.kotlin.src.main.shopping.ShoppingProductsAdapter
import com.softsquared.template.kotlin.src.main.shopping.ShoppingService
import com.softsquared.template.kotlin.src.main.shopping.models.ProductResult
import com.softsquared.template.kotlin.src.main.shopping.models.ProductsResponse
import java.text.DecimalFormat

class DetailFragment : BaseFragment<FragmentDetailBinding>(FragmentDetailBinding::bind, R.layout.fragment_detail)
    ,ShoppingFragmentInterface{

   //private val productNum : Int by lazy { requireArguments().getInt("productNum") }
    private  var productNum: Int = 0
    private var productPrice : Int = 0
    lateinit var productName : String



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments != null){
            productNum = requireArguments().getInt("productNum")
        }

        val mainAct = activity as MainActivity
        mainAct.bottomNavigationHide(true)

        //스크롤 중첩 막기
        binding.svDetailProduct.isNestedScrollingEnabled = true
        //디테일 정보 받아옴
        ShoppingService(this).tryGetDetail(productNum)
        //리뷰 정보 받아옴
        ShoppingService(this).tryGetReview(productNum)
        //리스트 받아옴
        ShoppingService(this).tryGetProducts()
        //stickyheader적용
        binding.svDetailProduct.run {
            header = binding.headerView
        }


        goToOrder()

        gotoWriteReview()

        //fab -> 맨위로 / sticky클릭 -> 특정위치로 이동 구성
        scrollspeificPart()



    }


    override fun onDestroyView() {
        super.onDestroyView()
        val mainAct = activity as MainActivity
        mainAct.bottomNavigationHide(false)
    }

    private fun scrollspeificPart(){
        binding.fabDetailUp.setOnClickListener {
            binding.svDetailProduct.scrollY = ScrollView.FOCUS_UP
        }

        //특정위치로 가게 되는거 만들기
        binding.tvDetailHeaderInformation.setOnClickListener {  }
    }

    override fun onResume() {
        super.onResume()
        val mainAct = activity as MainActivity
        mainAct.bottomNavigationHide(true)
    }

    private fun goToOrder(){
        //구매버튼클릭시 이동
        binding.btnDetailPurchase.setOnClickListener {
            val args = Bundle()
            val purchaseFragment = DetailPurchaseBottomSheetFragment()
            args.putString("productName",productName)
            args.putInt("productNum",productNum)
            args.putInt("productPrice",productPrice)
            purchaseFragment.arguments = args
            purchaseFragment.show(parentFragmentManager,"TEST")
        }
    }

    private fun gotoWriteReview(){
        //리뷰작성
        binding.tvDetailReviewWrite.setOnClickListener {
            val args = Bundle()
            val detailReviewWriteFragment = DetailReviewWriteFragment()
            args.putInt("productNum",productNum)
            detailReviewWriteFragment.arguments = args
            parentFragmentManager.beginTransaction().add(R.id.main_frm,detailReviewWriteFragment).commit()
        }

    }


    @SuppressLint("SetTextI18n")
    override fun onGetDetailSuccess(response: DetailResponse) {
        val dec = DecimalFormat("#,###,###")
        with(binding){
            //여기 썸네일 리스트형식으로 들어올때에 대해서 처리하기
            tvDetailBarTitle.text = response.result.product.productName
            tvDetailProductName.text = response.result.product.productName
            tvDetailCompany.text = response.result.product.productCom
            tvDetailClassification.text = response.result.product.productCate.toString()
            tvDetailReviewCnt.text = "("+response.result.product.reviewCnt.toString() + ")"
            tvDetailProductPrice.text = dec.format(response.result.product.productPrice) + "원"
            tvDetailProductAcuumulate.text = dec.format(response.result.product.productPrice/1000) + " P "
            tvDetailReviewPointAvg.text = response.result.product.reviewAvg.toString()
            rbDetailReviewStar.rating = response.result.product.reviewAvg.toFloat()
            tvDetailReviewCnt.text = response.result.product.reviewCnt.toString()
            tvDetailReviewCnt2.text = response.result.product.reviewCnt.toString()
            vp2DetailImage.apply {
                adapter = DetailImageAdapter(response.result.thumbnails) //내일 이곳 thumbnail로 수정예정
                orientation = ViewPager2.ORIENTATION_HORIZONTAL
            }
            rvDetailInformation.apply {
                adapter = DetailInformationAdapter(response.result.productImg)
                layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
            }
        }
        productName = response.result.product.productName
        productPrice = response.result.product.productPrice
    }

    override fun onGetDetailFailure(message: String) {
        showCustomToast("오류: $message")
    }

    override fun onGetReviewSuccess(response: DetailReviewResponse) {
        binding.rvDetailReview.apply {
            adapter = DetailReviewAdapter(response.reviewResult , object  :ItemClickListener<ReviewResult>{
                override fun onItemClick(pos: Int, item: ReviewResult) {
                    //여기 리뷰 상세페이지 시간남으면 이동해서 구현하기
                }
            })
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        }
    }


    override fun onGetReviewFailure(message: String) {
        showCustomToast("오류 : $message")
    }

    override fun onGetProductSuccess(response: ProductsResponse) {
        binding.rvDetailSimiliarProduct.apply {
            adapter = ShoppingProductsAdapter(response.productResult,object : ItemClickListener<ProductResult>{
                override fun onItemClick(pos: Int, item: ProductResult) {
                    val productNum : Int? = item.productNum
                    parentFragmentManager.beginTransaction().replace(R.id.main_frm,DetailFragment().apply{
                        arguments = Bundle().apply {
                            productNum?.let{putInt("productNum",productNum)}
                        }
                    }).addToBackStack(null).commit()
                }
            })
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        }
        binding.rvDetailAnotherProduct.apply {
            adapter = ShoppingProductsAdapter(response.productResult,object : ItemClickListener<ProductResult>{
                override fun onItemClick(pos: Int, item: ProductResult) {
                    val productNum : Int? = item.productNum
                    parentFragmentManager.beginTransaction().replace(R.id.main_frm,DetailFragment().apply{
                        arguments = Bundle().apply {
                            productNum?.let{putInt("productNum",productNum)}
                        }
                    }).addToBackStack(null).commit()
                }
            })
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        }
    }

    override fun onGetProductFailure(message: String) {
        showCustomToast("오류 : $message")
    }

    override fun onPostCartSuccess(response: DetailCartResponse) {}
    override fun onPostCartFailure(message: String) {}
    override fun onPostReviewSuccess(response: DetailReviewWriteResponse) {}
    override fun onPostReviewFailure(message: String) {}
}