package com.softsquared.template.kotlin.src.main.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.config.BaseFragment
import com.softsquared.template.kotlin.databinding.FragmentHomeBinding
import com.softsquared.template.kotlin.databinding.HomeTipslistBinding
import com.softsquared.template.kotlin.src.Buy.CartFragment
import com.softsquared.template.kotlin.src.main.MainActivity
import com.softsquared.template.kotlin.src.main.home.models.*
import com.softsquared.template.kotlin.src.main.home.recommend.RecommendFragment
import com.softsquared.template.kotlin.src.main.home.recommend.models.RecommendResponse
import com.softsquared.template.kotlin.src.main.shopping.Detail.DetailFragment
import kotlin.concurrent.thread

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::bind, R.layout.fragment_home)
    ,HomeFragmentInterface{

    private val TAG = "TEST"
    var iconList = arrayListOf<HomeIcon>() //아이콘 리스트
    //테스트용 선언
    private var eventList = arrayListOf<HomeEventData>()
    private var currentEventPage = 0 //이벤트 페이지 구현
    private var detailProductNum : Int = 0
    private var threadOn = true //이벤트 쓰레드 상태
    private lateinit var eventThread: Thread


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mainAct = activity as MainActivity
        mainAct.bottomNavigationHide(false)


        HomeService(this).tryGetHome()

        recommendReyclerViewControl() //리사이클러뷰 작동 함수 모아놓은곳 (서버와 통신없는곳)

        //이벤트 이미지 (뷰페이저 + 리사이클러뷰), 쓰레드로 이미지 자동 슬라이드
        eventPictureSlide()
        eventThread = Thread(PagerRunnable()) //이벤트 이미지 스크롤 thread로 이용해서 돌림
        eventThread.start() //eventthread

        //스크롤 내릴때 위로 fab 나오기,fab 처음 안보이게 설정
        binding.fabHomeUp.alpha = 0f
        binding.nestedScrollView.setOnScrollChangeListener(onScrollListener)
        //맨위로 뷰 올리는 fab컨트롤
        binding.fabHomeUp.setOnClickListener{
            binding.nestedScrollView.fullScroll(ScrollView.FOCUS_UP)
        }
        //bottomsheetdialog
        binding.fabHomeWriting.setOnClickListener {
            HomeBottomSheetFragment().show(parentFragmentManager, TAG)
        }

        binding.ibtnHomeCart.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.main_frm,CartFragment()).addToBackStack(null).commit()
        }

        binding.ivHomeTodaydeal.setOnClickListener {
            Log.d("----","$detailProductNum")

            val homeToDetailBundle  = Bundle()
            homeToDetailBundle.putInt("productNum",detailProductNum)
            parentFragmentManager.beginTransaction().replace(R.id.main_frm,DetailFragment().apply{
                arguments = homeToDetailBundle
            }).addToBackStack(null).commit()
        }

    }

    override fun onGetHomeSuccess(response: HomeResponse) {

        binding.rvHomeRecommend.apply {
            adapter = HomeRecommendAdapter(response.result.famousContents, object :ItemClickListener<FamousContent>{
                override fun onItemClick(pos: Int, item: FamousContent) {
                    val contentNum : Int = item.contentNum
                    val hometoRecommendBundle = Bundle()
                    hometoRecommendBundle.putInt("contentNum",contentNum)
                    parentFragmentManager.beginTransaction().replace(R.id.main_frm,RecommendFragment().apply {
                        arguments = hometoRecommendBundle
                    }).addToBackStack(null).commit()
                }
            })
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
            setHasFixedSize(true)
        }
        binding.rvHomeTips.apply {
            adapter = HomeTipsAdapter(response.result.contents, object : HomeTipClickListener<Content>{
                override fun onItemClick(pos: Int, item: Content) {
                    val contentNum : Int = item.contentNum
                    val hometoRecommendBundle = Bundle()
                    hometoRecommendBundle.putInt("contentNum",contentNum)
                    parentFragmentManager.beginTransaction().replace(R.id.main_frm,RecommendFragment().apply {
                        arguments = hometoRecommendBundle
                    }).addToBackStack(null).commit()
                }

                override fun onViewClick(view: HomeTipslistBinding, pos: Int) {
                    view.ibtnHomeTipListScrap.setBackgroundResource(R.drawable.ic_scrap_on)
                }
            })
            layoutManager = GridLayoutManager(context,2)
        }
        binding.rvHomeRecommndHouses.apply {
            adapter = HomeTipsAdapter(response.result.contents, object : HomeTipClickListener<Content>{
                override fun onItemClick(pos: Int, item: Content) {
                    val contentNum : Int = item.contentNum
                    parentFragmentManager.beginTransaction().replace(R.id.main_frm,RecommendFragment().apply {
                        arguments = Bundle().apply{
                            putInt("contentNum",contentNum)
                        }
                    }).addToBackStack(null).commit()

                }

                override fun onViewClick(view: HomeTipslistBinding, pos: Int) {
                    view.ibtnHomeTipListScrap.setOnClickListener{
                        view.ibtnHomeTipListScrap.setBackgroundResource(R.drawable.ic_scrap_on)
                    }
                }
            })
            layoutManager = GridLayoutManager(context,2)
        }

        Glide.with(this).load(response.result.products[0].productThumbnail).into(binding.ivHomeTodaydeal)
        binding.tvHomeTodaydealTitle.text = response.result.products[0].productName.toString()
        detailProductNum = response.result.products[0].productNum
    }

    override fun onGetHomeFailure(message: String) {
       showCustomToast("오류 : $message")
    }

    private fun recommendReyclerViewControl() {
        //메뉴아이콘
        val nameArray : Array<String> = resources.getStringArray(R.array.home_icon_name_list)
        val picArray  = resources.obtainTypedArray(R.array.home_icon_pic_list)
        for(i in nameArray.indices){
            iconList.add(HomeIcon(picArray.getResourceId(i,-1),nameArray[i]))
        }
        binding.rvHomeIcon.apply{
            adapter = HomeIconAdapter(iconList)
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
            setHasFixedSize(true)
        }
    }


    //플로팅 버튼 클릭시 위로 올라가게 하는것
    private val onScrollListener = object : NestedScrollView.OnScrollChangeListener{
        override fun onScrollChange(
            v: NestedScrollView,
            scrollX: Int,
            scrollY: Int,
            oldScrollX: Int,
            oldScrollY: Int
        ) {
            binding.fabHomeUp.alpha = getAlphaFloatingButton(scrollY)
        }
        private fun getAlphaFloatingButton(scrollY : Int) : Float{
            val dist = 1500 //테스트
            return if(scrollY < dist){ 0f }
            else{ 1f }
        }
    }

    //이벤트 이미지 관련 배열과 어댑터 연결
    private fun eventPictureSlide(){
        //이벤트 사진 -> 테스트지만 그대로 들고갈듯
        val eventArray = resources.obtainTypedArray(R.array.home_event_pic_list)
        for(i in 0 until 3){
            eventList.add(HomeEventData(eventArray.getResourceId(i,-1)))
        }
        binding.vp2HomeEvent.apply {
            adapter =HomeEventPictureAdapter(eventList)
            orientation =ViewPager2.ORIENTATION_HORIZONTAL
        }
    }

    //이벤트 - 핸들러를 통해 현재 페이지를 끝까지 가고 다시 앞으로 돌아오는것을 구현
    override fun onResume() {
        super.onResume()
        threadOn = true

    }

    override fun onPause() {
        super.onPause()
        threadOn = false
        eventThread.interrupt()
    }

    private fun setPage(){
        if(currentEventPage == eventList.size) {
            currentEventPage = 0
        }
        binding.vp2HomeEvent.setCurrentItem(currentEventPage,true)
        currentEventPage +=1
    }
    val handler = Handler(Looper.getMainLooper()) {
        setPage()
        true
    }

    inner class PagerRunnable : Runnable {
        override fun run() {
            while (threadOn) {
                try {
                    Thread.sleep(2000)
                    handler.sendEmptyMessage(0)
                } catch (e: InterruptedException) {
                    Log.d("thread", "thread_problem!")
                }
                if(!threadOn)
                    eventThread.interrupt()
            }
        }
    }


    override fun onGetRecommendSucccess(response: RecommendResponse) {}
    override fun onGetRecommenedFailure(message: String) {}
}