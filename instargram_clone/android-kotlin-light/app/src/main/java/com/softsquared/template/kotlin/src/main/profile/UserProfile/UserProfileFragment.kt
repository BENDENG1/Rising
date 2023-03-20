package com.softsquared.template.kotlin.src.main.profile.UserProfile

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.config.BaseFragment
import com.softsquared.template.kotlin.databinding.FragmentUserProfileBinding
import com.softsquared.template.kotlin.src.main.Follow.Fragment.FollowFragment
import com.softsquared.template.kotlin.src.main.profile.Fragment.ProfileSettingDialog
import com.softsquared.template.kotlin.src.main.profile.List.ProfileStoryAdapter
import com.softsquared.template.kotlin.src.main.profile.List.RecommendFriendAdapter
import com.softsquared.template.kotlin.src.main.profile.ProfilePartInterface
import com.softsquared.template.kotlin.src.main.profile.ProfileService
import com.softsquared.template.kotlin.src.main.profile.models.GetProfilePostResponse
import com.softsquared.template.kotlin.src.main.profile.models.GetProfileResponse
import com.softsquared.template.kotlin.src.main.profile.models.ProfileStoryList
import com.softsquared.template.kotlin.src.main.profile.models.RecommendFriendList

class UserProfileFragment : BaseFragment<FragmentUserProfileBinding>(FragmentUserProfileBinding::bind, R.layout.fragment_user_profile),ProfilePartInterface{

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
        RecommendFriendList(R.drawable.ic_home_pheed_picture,"추천친구 테스트"),
        RecommendFriendList(R.drawable.ic_home_pheed_picture,"추천친구 테스트"),
        RecommendFriendList(R.drawable.ic_home_pheed_picture,"추천친구 테스트"),
        RecommendFriendList(R.drawable.ic_home_pheed_picture,"추천친구 테스트"),
        RecommendFriendList(R.drawable.ic_home_pheed_picture,"추천친구 테스트"),
        RecommendFriendList(R.drawable.ic_home_pheed_picture,"추천친구 테스트"),
        RecommendFriendList(R.drawable.ic_home_pheed_picture,"추천친구 테스트"),
        RecommendFriendList(R.drawable.ic_home_pheed_picture,"추천친구 테스트"),
        RecommendFriendList(R.drawable.ic_home_pheed_picture,"추천친구 테스트"),
        RecommendFriendList(R.drawable.ic_home_pheed_picture,"추천친구 테스트")
    )


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerViewControl()
        tabLayoutControl()

        val user_id = ApplicationClass.sSharedPreferences.getInt("user_id",0) //이거 내 유저아이디임
        ProfileService(this).tryGetProfile(user_id)

        //추천친구 보고싶을때
        binding.ibtnUserProfileRecommendFriends.setOnClickListener {
            binding.clUserProfileRecommendFriend.visibility = View.VISIBLE
            binding.rvUserRecommendFriend.apply {
                adapter = RecommendFriendAdapter(recommendFriendList)
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
            }
        }

        with(binding) {
            tvUserProfileFollower.setOnClickListener { moveFollowFragment() }
            tvUserProfileCountFollower.setOnClickListener { moveFollowFragment() }
            tvUserProfileFollowing.setOnClickListener { moveFollowFragment() }
            tvUserProfileFollowing.setOnClickListener { moveFollowFragment() }
        }

        binding.ibtnUserProfileSetting.setOnClickListener {
            ProfileSettingDialog(requireContext())
        }

    }

    private fun moveFollowFragment(){
        ApplicationClass.sSharedPreferences.edit().putBoolean("is_user",true).apply()
        parentFragmentManager.beginTransaction().replace(R.id.main_frm, FollowFragment()).addToBackStack("userFollower").commit()
    }

    private fun tabLayoutControl(){
        binding.vpUserProfilePost.apply{
            adapter = UserProfileTabPageAdapter(this@UserProfileFragment)
        }

        TabLayoutMediator(binding.tabLayoutUserProfile,binding.vpUserProfilePost){ tab, position ->
            when(position){
                0 -> tab.setIcon(R.drawable.ic_profile_tab_post)
                1 -> tab.setIcon(R.drawable.ic_profile_tab_reels)
                2 -> tab.setIcon(R.drawable.ic_profile_tab_tag)
            }
        }.attach()
    }

    private fun recyclerViewControl(){
        binding.rvUserProfileStory.apply {
            adapter = ProfileStoryAdapter(profileStoryList)
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,true)
        }
    }



    @SuppressLint("ResourceAsColor", "UseCompatLoadingForDrawables")
    override fun onGetProfileSuccess(response: GetProfileResponse) {
        binding.btnUserProfileNickName.text = response.profileResult.nickname
        binding.tvUserProfileName.text = response.profileResult.name
        Glide.with(this).load(response.profileResult.profile_image_url).circleCrop().into(binding.ivUserProfilePicture)
        binding.tvUserProfileCountPost.text = response.profileResult.post_count.toString()
        binding.tvUserProfileCountFollower.text = response.profileResult.follower_count.toString()
        binding.tvUserProfileCountFollowing.text = response.profileResult.following_count.toString()
        binding.tvUserProfileIntroduce.text = response.profileResult.introduce

        if(response.profileResult.follow_status==1){
            with(binding.btnUserProfileFollow){
                setTextColor(R.color.white)
                text = "팔로잉"
                background = requireContext().getDrawable(R.drawable.follow_border_blue_rectangle)
            }
        }

        if(response.profileResult.story_status == 0){
            binding.ivUserProfilePicture.borderColor = R.color.transparent
        }

        val sharedpref = ApplicationClass.sSharedPreferences.edit()
        sharedpref.putString("myProfile",response.profileResult.profile_image_url)
        sharedpref.putInt("followerCount",response.profileResult.follower_count)
        sharedpref.putInt("followingCount",response.profileResult.following_count)
        sharedpref.putInt("connectedCount",response.profileResult.connected_count)
        sharedpref.apply()

        if(binding.tvUserProfileName.text.isEmpty())
            binding.tvUserProfileName.visibility = View.GONE
        if(binding.tvUserProfileIntroduce.text.isEmpty())
            binding.tvUserProfileIntroduce.visibility = View.GONE

    }

    override fun onGetProfileFailure(message: String) {
        showCustomToast("실패 사유 : $message")
    }

    override fun onGetProfilePostSuccess(response: GetProfilePostResponse) {
    }

    override fun onGetProfilePostFailure(message: String) {
    }
}