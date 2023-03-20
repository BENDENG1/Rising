package com.softsquared.template.kotlin.src.main.shopping

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.ScrollView
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.BaseFragment
import com.softsquared.template.kotlin.databinding.FragmentShoppingBinding
import com.softsquared.template.kotlin.src.Buy.CartFragment
import com.softsquared.template.kotlin.src.main.ItemClickListener
import com.softsquared.template.kotlin.src.main.shopping.Detail.DetailFragment
import com.softsquared.template.kotlin.src.main.shopping.Detail.models.DetailCartResponse
import com.softsquared.template.kotlin.src.main.shopping.Detail.models.DetailResponse
import com.softsquared.template.kotlin.src.main.shopping.Detail.models.DetailReviewResponse
import com.softsquared.template.kotlin.src.main.shopping.Detail.models.DetailReviewWriteResponse
import com.softsquared.template.kotlin.src.main.shopping.models.ProductResult
import com.softsquared.template.kotlin.src.main.shopping.models.ShoppingCategoryData
import com.softsquared.template.kotlin.src.main.shopping.models.ShoppingImageData
import com.softsquared.template.kotlin.src.main.shopping.models.ProductsResponse

class ShoppingFragment :BaseFragment<FragmentShoppingBinding>(FragmentShoppingBinding::bind, R.layout.fragment_shopping)
    ,ShoppingFragmentInterface{ //사용을 위해 interface
    private val shoppingImageList = arrayListOf<ShoppingImageData>()
    private val shoppingCategoryList = arrayListOf<ShoppingCategoryData>()
    private var currentEventPage = 0
    private var threadOn = true //이벤트 쓰레드 상태
    private lateinit var shoppingThread: Thread


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        iconRVControl()
        ShoppingService(this).tryGetProducts()

        //fab버튼 유무와 스크롤업
        binding.nsvShopping.setOnScrollChangeListener(onScrollListener)
        binding.fabShoppingUp.setOnClickListener{
            binding.nsvShopping.fullScroll(ScrollView.FOCUS_UP)
        }

        binding.ibtnSearchShoppingBasket.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.main_frm,CartFragment()).addToBackStack(null).commit()
        }


    }

    override fun onResume() {
        super.onResume()
        threadOn = true

    }

    override fun onPause() {
        super.onPause()
        threadOn = false
        shoppingThread.interrupt()
    }

    private fun iconRVControl(){
        //쇼핑 멘위 이벤트 뷰페이저 + 리사이클러뷰 + 쓰레드
        eventPictureSlide()
        //카테고리 리사이클러뷰 실행
        categoryRecyclerView()

        shoppingThread = Thread(PagerRunnable()) //이벤트 이미지 스크롤 thread로 이용해서 돌림
        shoppingThread.start() //eventthread
    }

    //이벤트 - 핸들러를 통해 현재 페이지를 끝까지 가고 다시 앞으로 돌아오는것을 구현
    val handler = Handler(Looper.getMainLooper()) {
        if(currentEventPage == shoppingImageList.size) {
            currentEventPage = 0
        }
        binding.vp2ShoppingImage.setCurrentItem(currentEventPage,true)
        currentEventPage +=1
        true
    }

    inner class PagerRunnable : Runnable {
        override fun run() {
            while (true) {
                try {
                    Thread.sleep(2000)
                    handler.sendEmptyMessage(0)
                } catch (e: InterruptedException) {
                    Log.d("thread", "threadProblem!")
                }
                if(!threadOn)
                    shoppingThread.interrupt()
            }
        }
    }

    private fun categoryRecyclerView(){
        val categoryNameArray : Array<String> = resources.getStringArray(R.array.shopping_category_name_list)
        val categoryIconArray = resources.obtainTypedArray(R.array.shopping_category_icon_list)

        for(i in categoryNameArray.indices){
            shoppingCategoryList.add(ShoppingCategoryData(categoryIconArray.getResourceId(i,-1),categoryNameArray[i]))
        }
        binding.rvShoppingCategory.apply {
            adapter = ShoppingCategoryAdapter(shoppingCategoryList)
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
            setHasFixedSize(true)
        }
    }


    @SuppressLint("Recycle")
    private fun eventPictureSlide(){
        val shoppingEventArray = resources.obtainTypedArray(R.array.home_event_pic_list)
        for(i in 0 until 4){
            shoppingImageList.add(ShoppingImageData(shoppingEventArray.getResourceId(i,-1)))
        }
        binding.vp2ShoppingImage.apply {
            adapter = ShoppingImageAdapter(shoppingImageList)
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
        }

    }


    override fun onGetProductSuccess(response: ProductsResponse) {
        for (Product in response.productResult){
            Log.d("shoppingFragment",Product.toString())
        }
        binding.rvShoppingTodayRecommend.apply {
            adapter = ShoppingProductsAdapter(response.productResult,object :
                ItemClickListener<ProductResult> {
                override fun onItemClick(pos: Int, item: ProductResult) {
                    val productNum : Int? = item.productNum
                    parentFragmentManager.beginTransaction().replace(R.id.main_frm,DetailFragment().apply {
                        arguments = Bundle().apply {
                            productNum?.let { putInt("productNum", productNum) } //key,value
                        }
                    }).addToBackStack(null).commit()
                }
            })
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
//            setHasFixedSize(true)
        }
        binding.rvShoppingTodayDeal.apply {
            adapter = ShoppingProductsAdapter(response.productResult, object :
                ItemClickListener<ProductResult> {
                override fun onItemClick(pos: Int, item: ProductResult) {
                    val productNum : Int? = item.productNum
                    parentFragmentManager.beginTransaction().replace(R.id.main_frm,DetailFragment().apply {
                        arguments = Bundle().apply {
                            productNum?.let { putInt("productNum", productNum) } //key,value
                        }
                    }).addToBackStack(null).commit()
                }
            })
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
//            setHasFixedSize(true)
        }
        binding.rvShoppingProductLately.apply{
            adapter = ShoppingProductsAdapter(response.productResult, object :
                ItemClickListener<ProductResult> {
                override fun onItemClick(pos: Int, item: ProductResult) {
                    val productNum : Int? = item.productNum
                    parentFragmentManager.beginTransaction().replace(R.id.main_frm,DetailFragment().apply {
                        arguments = Bundle().apply {
                            productNum?.let { putInt("productNum", productNum) } //key,value
                        }
                    }).addToBackStack(null).commit()
                }
            })
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
//            setHasFixedSize(true)
        }
        binding.rvShoppingRelation.apply {
            adapter = ShoppingProductsAdapter(response.productResult, object :
                ItemClickListener<ProductResult> {
                override fun onItemClick(pos: Int, item: ProductResult) {
                    val productNum : Int? = item.productNum
                    parentFragmentManager.beginTransaction().replace(R.id.main_frm,DetailFragment().apply {
                        arguments = Bundle().apply {
                            productNum?.let { putInt("productNum", productNum) } //key,value
                        }
                    }).addToBackStack(null).commit()
                }
            })
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
//            setHasFixedSize(true)
        }
        binding.rvShoppingProductPoplar.apply {
            adapter = ShoppingProductsAdapter(response.productResult, object :
                ItemClickListener<ProductResult> {
                override fun onItemClick(pos: Int, item: ProductResult) {
                    val productNum : Int? = item.productNum
                    parentFragmentManager.beginTransaction().replace(R.id.main_frm,DetailFragment().apply {
                        arguments = Bundle().apply {
                            productNum?.let { putInt("productNum", productNum) } //key,value
                        }
                    }).addToBackStack(null).commit()
                }
            })
            layoutManager = GridLayoutManager(context,2)
//            setHasFixedSize(true)
        }
    }

    override fun onGetProductFailure(message: String) {
        showCustomToast("오류 : $message")
    }

    //스크롤
    private val onScrollListener = object : NestedScrollView.OnScrollChangeListener{
        override fun onScrollChange(
            v: NestedScrollView,
            scrollX: Int,
            scrollY: Int,
            oldScrollX: Int,
            oldScrollY: Int
        ) {
            binding.fabShoppingUp.alpha = getAlphaFloatingButton(scrollY)
        }
        private fun getAlphaFloatingButton(scrollY : Int) : Float{
            val dist = 1500 //테스트
            return if(scrollY < dist){ 0f }
            else{ 1f }
        }
    }


    override fun onGetDetailSuccess(response: DetailResponse) {}
    override fun onGetDetailFailure(message: String) {}
    override fun onPostCartSuccess(response: DetailCartResponse) {}
    override fun onPostCartFailure(message: String) {}
    override fun onGetReviewSuccess(response: DetailReviewResponse) {}
    override fun onGetReviewFailure(message: String) {}
    override fun onPostReviewSuccess(response: DetailReviewWriteResponse) {}
    override fun onPostReviewFailure(message: String) {}
}