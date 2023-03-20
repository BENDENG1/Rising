package com.softsquared.template.kotlin.src.main.Story

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.Window
import android.view.WindowInsetsController
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.config.BaseFragment
import com.softsquared.template.kotlin.databinding.FragmentStoryBinding
import com.softsquared.template.kotlin.src.main.MainActivity
import com.softsquared.template.kotlin.src.main.Story.models.GetFullStoriesResponse
import com.softsquared.template.kotlin.src.main.Story.models.GetSpecificStoriesResponse
import com.softsquared.template.kotlin.src.main.Story.models.PatchDeleteStoryResponse
import com.softsquared.template.kotlin.util.TimeConversion

class StoryFragment : BaseFragment<FragmentStoryBinding>(FragmentStoryBinding::bind, R.layout.fragment_story)
    ,StoryFragmentInterface{

    private var currentStoryId = 0


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val myStory = ApplicationClass.sSharedPreferences.getBoolean("myStory",false)
        val userId = ApplicationClass.sSharedPreferences.getInt("user_id",0)
        isMyStory(myStory)

        StoryService(this).tryGetFullStories(user_id = userId)


        deleteStory(userId)
    }


    @RequiresApi(Build.VERSION_CODES.R)
    override fun onResume() {
        super.onResume()
        val mainAct = activity as MainActivity
        mainAct.hideNavigationBar(true)

        //색상 바꾸기..
        activity?.window?.statusBarColor = ContextCompat.getColor(requireContext(),  R.color.black)
        activity?.window?.insetsController?.setSystemBarsAppearance(WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS, WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS)
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onDestroyView() {
        super.onDestroyView()
        activity?.window?.statusBarColor = ContextCompat.getColor(requireContext(),R.color.white)
        activity?.window?.insetsController?.setSystemBarsAppearance(WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS, WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS)
    }

    private fun deleteStory(userId : Int){
        binding.ibtnStoryUsersMore.setOnClickListener {
            binding.clStoryMore.visibility = View.VISIBLE
        }

        binding.vpStoryStories.setOnClickListener{
            binding.clStoryMore.visibility = View.GONE
        }
        binding.tvStoryDelete.setOnClickListener {
            StoryService(this).tryPatchDelteStory(currentStoryId)
            StoryService(this).tryGetFullStories(user_id = userId)
            binding.clStoryMore.visibility = View.GONE
        }
    }


    private fun isMyStory(myStory : Boolean){
        if(myStory){
            binding.clStoryMe.visibility = View.VISIBLE
            binding.clStoryUser.visibility = View.GONE
        }else{
            binding.clStoryMe.visibility = View.GONE
            binding.clStoryUser.visibility = View.VISIBLE
        }
    }

    override fun onGetFullStoriesSuccess(response: GetFullStoriesResponse) {

        if(response.fullStoriesResult.isEmpty()){
            parentFragmentManager.popBackStack()
        }

        Glide.with(this).load(response.fullStoriesResult[0].story_viewer_profile_image_urls).circleCrop().into(binding.civStoryUsersViewer1)
        Glide.with(this).load(response.fullStoriesResult[1].story_viewer_profile_image_urls).circleCrop().into(binding.civStoryUsersViewer2)

        val viewPager = binding.vpStoryStories
        binding.vpStoryStories.apply {
            adapter = FullStoriesAdapter(response.fullStoriesResult)
        }

        //뷰페이저2 regitsterOnpageChangeCallback을 통해 현재 스토리를 읽었는지 안읽었는지에 대한 통신을 이룰거임
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                currentStoryId = response.fullStoriesResult[position].user_story_id
                StoryService(this@StoryFragment).tryGetSpecificStory(response.fullStoriesResult[position].user_story_id)
            }
        })
    }

    override fun onGetFullStoriesFailure(message: String) {
        showCustomToast("오류 : $message")
    }


    override fun onGetSpecificStoriesSuccess(response: GetSpecificStoriesResponse) {

    }
    override fun onGetSpecificStoriesFailure(message: String) {
        showCustomToast("오류 : $message")
    }

    override fun onPatchDeleteStorySuccess(response: PatchDeleteStoryResponse) {
        Log.d("testtest",response.message.toString())
        Log.d("testtest",response.isSuccess.toString())
        Log.d("testtest",response.code.toString())
//        Log.d("testtest",response.result.toString())

    }

    override fun onPatchDeleteStoryFailure(message: String) {
        showCustomToast("오류 : $message")
    }
}