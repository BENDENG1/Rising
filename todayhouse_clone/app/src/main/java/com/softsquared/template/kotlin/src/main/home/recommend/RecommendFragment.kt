package com.softsquared.template.kotlin.src.main.home.recommend

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.widget.NestedScrollView
import com.bumptech.glide.Glide
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.BaseFragment
import com.softsquared.template.kotlin.databinding.FragmentHomeRecommendBinding
import com.softsquared.template.kotlin.src.main.MainActivity
import com.softsquared.template.kotlin.src.main.home.HomeFragmentInterface
import com.softsquared.template.kotlin.src.main.home.HomeService
import com.softsquared.template.kotlin.src.main.home.models.HomeResponse
import com.softsquared.template.kotlin.src.main.home.recommend.models.RecommendResponse
import java.text.SimpleDateFormat

class RecommendFragment : BaseFragment<FragmentHomeRecommendBinding>
    (FragmentHomeRecommendBinding::bind , R.layout.fragment_home_recommend),HomeFragmentInterface {


    private var contentNum: Int = 0
    private var isScrap = false


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //바텀 네비게이션 삭제
        val mainAct = activity as MainActivity
        mainAct.bottomNavigationHide(true)

        if (arguments != null){
            contentNum = requireArguments().getInt("contentNum")
        }

        binding.clToolbar.bringToFront()
        HomeService(this).tryGetRecommend(contentNum = contentNum)

        binding.ivRecommendScrap.setOnClickListener {
            if(isScrap){
                binding.ivRecommendScrap.setBackgroundResource(R.drawable.ic_scrap_off)
                isScrap = !isScrap
            }
            else{
                binding.ivRecommendScrap.setBackgroundResource(R.drawable.ic_scrap_on)
                isScrap = !isScrap
            }
        }

        //스크롤할대 상단바 스크롤에 반응하게 설정
        binding.nestedScrollViewRecommend.setOnScrollChangeListener(onScrollListener)
        binding.viewToolbar.visibility = View.GONE
        binding.ivRecommendProfile.visibility = View.GONE
        binding.tvRecommendBarId.visibility = View.GONE

        Glide.with(this).load(R.drawable.aaaaa).circleCrop().into(binding.ivRecommendProfile)
        Glide.with(this).load(R.drawable.aaaaa).circleCrop().into(binding.ivRecommendMainProfie)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        val mainAct = activity as MainActivity
        mainAct.bottomNavigationHide(false)
    }


    private val onScrollListener = object : NestedScrollView.OnScrollChangeListener {
        override fun onScrollChange(
            v: NestedScrollView,
            scrollX: Int,
            scrollY: Int,
            oldScrollX: Int,
            oldScrollY: Int
        ) {
            binding.viewToolbar.alpha = getAlphaFloatingButton(scrollY)
        }


        private fun getAlphaFloatingButton(scrollY: Int): Float {
            val dist = 0
            return if (scrollY <= dist) {
                with(binding){
                    ibtnRecommendBack.setImageResource(R.drawable.ic_home_recommend_back)
                    ibtnRecommendHome.setImageResource(R.drawable.ic_home_recommend_home)
                    ibtnRecommendMore.setImageResource(R.drawable.ic_home_recommend_more)
                    ivRecommendProfile.visibility = View.GONE
                    tvRecommendBarId.visibility = View.GONE
                    viewToolbar.visibility = View.GONE
                }
                0f
            } else {
                with(binding){
                    ibtnRecommendBack.setImageResource(R.drawable.ic_home_recommend_back_black)
                    ibtnRecommendHome.setImageResource(R.drawable.ic_home_recommend_home_black)
                    ibtnRecommendMore.setImageResource(R.drawable.ic_home_recommend_more_black)
                    ivRecommendProfile.visibility = View.VISIBLE
                    tvRecommendBarId.visibility = View.VISIBLE
                    viewToolbar.visibility = View.VISIBLE
                }
                1f
            }
        }
    }

    override fun onGetHomeSuccess(response: HomeResponse) {}

    override fun onGetHomeFailure(message: String) {}

    @SuppressLint("SetTextI18n")
    override fun onGetRecommendSucccess(response: RecommendResponse) {
        if(response.result.userImg != null){
            Glide.with(this).load(response.result.userImg).circleCrop().into(binding.ivRecommendProfile)
            Glide.with(this).load(response.result.userImg).circleCrop().into(binding.ivRecommendMainProfie)
        }
        if(response.result.contentImg != null){
            Glide.with(this).load(response.result.contentImg).into(binding.ivRecommendContentImg)
            Glide.with(this).load(response.result.contentImg).into(binding.ivRecommendContentImg2)
        }
        binding.tvRecommendBarId.text = response.result.userNickName
        binding.tvRecommendTitle.text = response.result.contentTitle
        binding.tvRecommmendMainId.text = response.result.userNickName
        binding.tvRecommendWhen.text = convertTimestampToDate(response.result.createdAt)
        when (response.result.form){
            1 ->{binding.tvRecommendForm.text = "소파"}
            2 ->{binding.tvRecommendForm.text = "침대"}
            3 ->{binding.tvRecommendForm.text = "의자"}
            4 ->{binding.tvRecommendForm.text = "테이블"}
        }
        when (response.result.size){
            0 ->{binding.tvRecommendSize.text = "10평미만"}
            1 ->{binding.tvRecommendSize.text = "20평미만"}
            2 ->{binding.tvRecommendSize.text = "30평미만"}
            3 ->{binding.tvRecommendSize.text = "40평미만"}
            4 ->{binding.tvRecommendSize.text = "50평미만"}
            5 ->{binding.tvRecommendSize.text = "50평이상"}
        }
        when(response.result.style){
            0 -> {binding.tvRecommendStyle.text = "모던"}
            1 -> {binding.tvRecommendStyle.text = "북유럽"}
            2 -> {binding.tvRecommendStyle.text = "빈티지"}
            3 -> {binding.tvRecommendStyle.text = "내추럴"}
            4 -> {binding.tvRecommendStyle.text = "프로방스&로멘틱"}
            5 -> {binding.tvRecommendStyle.text = "클래식&앤틱"}
            6 -> {binding.tvRecommendStyle.text = "한국&아시아"}
            7 -> {binding.tvRecommendStyle.text = "유니크"}
        }
        when (response.result.contentCate){
            1 -> {binding.tvRecommendContentCate.text = "원룸"}
            2 -> {binding.tvRecommendContentCate.text = "거실"}
            3 -> {binding.tvRecommendContentCate.text = "침실"}
            4 -> {binding.tvRecommendContentCate.text = "주방"}
            5 -> {binding.tvRecommendContentCate.text = "욕실"}
            6 -> {binding.tvRecommendContentCate.text = "아이방"}
            7 -> {binding.tvRecommendContentCate.text = "드레스룸"}
            8 -> {binding.tvRecommendContentCate.text = "서재&작업실"}
            9 -> {binding.tvRecommendContentCate.text = "베란다"}
        }
        binding.tvRecommendContents.text = response.result.contents
    }

    override fun onGetRecommenedFailure(message: String) {
        showCustomToast("오류 : $message")
    }

    @SuppressLint("SimpleDateFormat")
    private fun convertTimestampToDate(timestamp: Long) :String{
        val sdf = SimpleDateFormat("yyyy년MM월dd일")
        val date = sdf.format(timestamp)
        return date
    }
}