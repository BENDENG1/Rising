package com.softsquared.template.kotlin.src.main.search.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.config.BaseFragment
import com.softsquared.template.kotlin.databinding.FragmentSearchPostBinding
import com.softsquared.template.kotlin.src.main.MainActivity
import com.softsquared.template.kotlin.src.main.home.HomeFragmentInterface
import com.softsquared.template.kotlin.src.main.home.PheedPictureAdapter
import com.softsquared.template.kotlin.src.main.home.models.*
import com.softsquared.template.kotlin.src.main.search.SearchFragmentInterface
import com.softsquared.template.kotlin.src.main.search.SearchOneAdapter
import com.softsquared.template.kotlin.src.main.search.SearchService
import com.softsquared.template.kotlin.src.main.search.models.GetSearchIdResponse
import com.softsquared.template.kotlin.src.main.search.models.GetSearchOneResponse
import com.softsquared.template.kotlin.src.main.search.models.GetSearchPostResponse
import com.softsquared.template.kotlin.util.TimeConversion.Companion.intervalBetweenDateText

class SearchPostFragment : BaseFragment<FragmentSearchPostBinding>(FragmentSearchPostBinding::bind , R.layout.fragment_search_post)
    ,SearchFragmentInterface,HomeFragmentInterface {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val postId = ApplicationClass.sSharedPreferences.getInt("post_id",0)

        SearchService(this).tryGetSearchOne(postId)



    }

    override fun onResume() {
        super.onResume()
        val mainAct = activity as MainActivity
        mainAct.hideNavigationBar(false)
    }



    @SuppressLint("ResourceAsColor", "SetTextI18n")
    override fun onGetSearchOneSuccess(response: GetSearchOneResponse) {
        val result = response.searchOneResult
        Glide.with(this@SearchPostFragment).load(result.profilePicture).circleCrop().into(binding.ivSearchOneProfile)
        binding.tvSearchOneName.text = response.searchOneResult.profileName
        binding.tvSearchOneName2.text =response.searchOneResult.profileName
        binding.tvSearchOneLikeName.text = "좋아요 ${response.searchOneResult.likeCount}개"

        if(result.searchOnephotos.size <= 1){
            binding.tvSearchOnePageCount.setBackgroundColor(R.color.transparent)
            binding.tvSearchOnePageCount.visibility =View.GONE
        }


        if(result.SearchOnelikeOn.on ==1){
            Glide.with(this).load(R.drawable.ic_home_pheed_like_on).into(binding.ibtnSearchOneLike)
        }else{
            Glide.with(this).load(R.drawable.ic_home_like).into(binding.ibtnSearchOneLike)
        }

        if(result.searchOneScrapOn.on ==1){
            Glide.with(this).load(R.drawable.ic_scrap_on).into(binding.ibtnSearchOneScrap)
        }else{
            Glide.with(this).load(R.drawable.ic_home_scrap).into(binding.ibtnSearchOneScrap)
        }
        if(result.content.isNotEmpty()){
            binding.tvSearchOneDetail.text = result.content
        }
        binding.tvSearchOneWhen.text = intervalBetweenDateText(result.createdAt.toString())
        if(result.updatedAt.isNotEmpty()){
            binding.tvSearchOneWhen.text = intervalBetweenDateText(result.updatedAt.toString())
        }

        //하트,좋아요
        binding.ibtnSearchOneLike.setOnClickListener {

        }



        binding.vpSearchOnePicture.apply {
            adapter = SearchOneAdapter(response.searchOneResult.searchOnephotos)
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                @SuppressLint("SetTextI18n")
                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int,
                ) {
                    super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                    binding.tvSearchOnePageCount.text = "${position + 1}/${binding.vpSearchOnePicture.adapter?.itemCount}"
                }
            })
        }

    }

    override fun onGetSearchOneFailure(message: String) {
        showCustomToast("오류 : $message")
    }

    override fun onGetSearchPostSuceess(response: GetSearchPostResponse) {}
    override fun onGetSearchPostFailure(message: String) {}
    override fun onGetSearchIdSuccess(response: GetSearchIdResponse) {}
    override fun onGetSearchIdFailure(message: String) {}
    override fun onGetHomePheedSuccess(response: GetHomePheedResponse) {}
    override fun onGetHomePheedFailure(message: String) {}

    override fun onGetHomeStorySuccess(response: GetHomeStoryResponse) {}

    override fun onGetHomeStoryFailure(message: String) {}

    override fun onPostPheedLikeSuccess(response: PostPheedLikeRespose) {}

    override fun onPostPheedLikeFailure(message: String) {}

    override fun onPatchPheedLikeSuccess(response: PatchPheedLikeResponse) {}

    override fun onPatchPheedLikeFailure(message: String) {}

    override fun onPostPheedScrapSuccess(response: PostPheedScrapResponse) {}

    override fun onPostPheedScrapFailure(message: String) {}

    override fun onPatchPheedScrapSuccess(response: PatchPheedScrapResponse) {}

    override fun onPatchPheedScrapFailure(message: String) {}
}