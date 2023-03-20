package com.softsquared.template.kotlin.src.main.profile.Fragment

import android.annotation.SuppressLint
import android.app.ProgressDialog.show
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.ApplicationClass.Companion.sSharedPreferences
import com.softsquared.template.kotlin.config.BaseFragment
import com.softsquared.template.kotlin.databinding.FragmentProfileBinding
import com.softsquared.template.kotlin.src.main.profile.*
import com.softsquared.template.kotlin.src.main.Follow.Fragment.FollowFragment
import com.softsquared.template.kotlin.src.main.MainActivity
import com.softsquared.template.kotlin.src.main.profile.List.ProfileStoryAdapter
import com.softsquared.template.kotlin.src.main.profile.List.ProfileTabPageAdapter
import com.softsquared.template.kotlin.src.main.profile.List.RecommendFriendAdapter
import com.softsquared.template.kotlin.src.main.profile.models.*

class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::bind, R.layout.fragment_profile)
    , ProfilePartInterface {



    //api나오면 수정
    private val profileStoryList = listOf<ProfileStoryList>(
        ProfileStoryList(R.drawable.ic_profile_picture,"스토리 테스트 중"),
        ProfileStoryList(R.drawable.ic_profile_picture,"스토리 테스트 중"),
        ProfileStoryList(R.drawable.ic_profile_picture,"스토리 테스트 중"),
        ProfileStoryList(R.drawable.ic_profile_picture,"스토리 테스트 중"),
        ProfileStoryList(R.drawable.ic_profile_picture,"스토리 테스트 중"),
        ProfileStoryList(R.drawable.ic_profile_picture,"스토리 테스트 중"),
        ProfileStoryList(R.drawable.ic_profile_picture,"스토리 테스트 중"),
        ProfileStoryList(R.drawable.ic_profile_picture,"스토리 테스트 중")
    )
    private val recommendFriendList = listOf(
        RecommendFriendList(R.drawable.ic_home_pheed_picture,"추천친구 테스트"),RecommendFriendList(R.drawable.ic_home_pheed_picture,"추천친구 테스트"),
        RecommendFriendList(R.drawable.ic_home_pheed_picture,"추천친구 테스트"),RecommendFriendList(R.drawable.ic_home_pheed_picture,"추천친구 테스트"),
        RecommendFriendList(R.drawable.ic_home_pheed_picture,"추천친구 테스트"),RecommendFriendList(R.drawable.ic_home_pheed_picture,"추천친구 테스트"),
        RecommendFriendList(R.drawable.ic_home_pheed_picture,"추천친구 테스트"),RecommendFriendList(R.drawable.ic_home_pheed_picture,"추천친구 테스트"),
        RecommendFriendList(R.drawable.ic_home_pheed_picture,"추천친구 테스트"),RecommendFriendList(R.drawable.ic_home_pheed_picture,"추천친구 테스트")
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerViewControl()
        tabLayoutControl()

        //프로필 관련 데이터 통신
        val my_id = sSharedPreferences.getInt("my_id",0) //이거 내 유저아이디임
        ProfileService(this).tryGetProfile(my_id)

        //추천친구 보고싶을때
        binding.ibtnProfileRecommendFriends.setOnClickListener {
            binding.clProfileRecommendFriend.visibility = View.VISIBLE
            binding.rvRecommendFriend.apply {
                adapter = RecommendFriendAdapter(recommendFriendList)
                layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
            }
        }

        with(binding) {
            tvProfileFollower.setOnClickListener { moveFollowFragment() }
            tvProfileCountFollower.setOnClickListener { moveFollowFragment() }
            tvProfileFollowing.setOnClickListener { moveFollowFragment() }
            tvProfileFollowing.setOnClickListener { moveFollowFragment() }
        }

        binding.ibtnProfileSetting.setOnClickListener {
            ProfileSettingDialog(requireContext())
        }

    }

    override fun onResume() {
        super.onResume()
        val mainAct = activity as MainActivity
        mainAct.hideNavigationBar(false)
    }

    private fun moveFollowFragment(){
        sSharedPreferences.edit().putBoolean("is_user",false).apply()
        parentFragmentManager.beginTransaction().replace(R.id.main_frm, FollowFragment()).addToBackStack("follower").commit()
    }

    private fun tabLayoutControl(){
        binding.vpProfilePost.apply{
            adapter = ProfileTabPageAdapter(this@ProfileFragment)
        }

        TabLayoutMediator(binding.tabLayoutProfile,binding.vpProfilePost){ tab, position ->
            when(position){
                0 -> tab.setIcon(R.drawable.ic_profile_tab_post)
                1 -> tab.setIcon(R.drawable.ic_profile_tab_reels)
                2 -> tab.setIcon(R.drawable.ic_profile_tab_tag)
            }
        }.attach()
    }

    private fun recyclerViewControl(){
        binding.rvProfileStory.apply {
            adapter = ProfileStoryAdapter(profileStoryList)
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        }
    }


    @SuppressLint("CommitPrefEdits", "ResourceAsColor")
    override fun onGetProfileSuccess(response: GetProfileResponse) {
        binding.btnProfileNickName.text = response.profileResult.nickname
        binding.tvProfileName.text = response.profileResult.name
        Glide.with(this).load(response.profileResult.profile_image_url).circleCrop().into(binding.ivProfilePicture)
        binding.tvProfileCountPost.text = response.profileResult.post_count.toString()
        binding.tvProfileCountFollower.text = response.profileResult.follower_count.toString()
        binding.tvProfileCountFollowing.text = response.profileResult.following_count.toString()
        binding.tvProfileIntroduce.text = response.profileResult.introduce

        if(response.profileResult.story_status == 0){
            binding.ivProfilePicture.borderColor = R.color.transparent
        }

        val sharedpref = sSharedPreferences.edit()
        sharedpref.putString("myProfile",response.profileResult.profile_image_url)
        sharedpref.putInt("followerCount",response.profileResult.follower_count)
        sharedpref.putInt("followingCount",response.profileResult.following_count)
        sharedpref.apply()

        if(binding.tvProfileName.text.isEmpty())
            binding.tvProfileName.visibility = View.GONE
        if(binding.tvProfileIntroduce.text.isEmpty())
            binding.tvProfileIntroduce.visibility = View.GONE

//        var userId :Int = 0
//        userId = response.profileResult.user_id //팔로우, 팔로잉 파트를 들어가게 되면 진행

    }


    override fun onGetProfileFailure(message: String) {
        showCustomToast("실패 사유 : $message")
    }

    override fun onGetProfilePostSuccess(response: GetProfilePostResponse) {}

    override fun onGetProfilePostFailure(message: String) {}

}