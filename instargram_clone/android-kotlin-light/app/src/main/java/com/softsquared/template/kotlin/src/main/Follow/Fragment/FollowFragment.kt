package com.softsquared.template.kotlin.src.main.Follow.Fragment

import android.os.Bundle
import android.view.View
import com.google.android.material.tabs.TabLayoutMediator
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.config.BaseFragment
import com.softsquared.template.kotlin.databinding.FragmentFollowBinding
import com.softsquared.template.kotlin.src.main.Follow.FollowFragmentInterface
import com.softsquared.template.kotlin.src.main.Follow.List.FollowTabPageAdapter
import com.softsquared.template.kotlin.src.main.Follow.List.UserFollowTabPageAdapter
import com.softsquared.template.kotlin.src.main.Follow.models.*
import com.softsquared.template.kotlin.src.main.MainActivity


class FollowFragment : BaseFragment<FragmentFollowBinding>(FragmentFollowBinding::bind,R.layout.fragment_follow),FollowFragmentInterface {



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val followerCount = ApplicationClass.sSharedPreferences.getInt("followerCount",0)
        val followingCount = ApplicationClass.sSharedPreferences.getInt("followingCount",0)
        val connectedCount = ApplicationClass.sSharedPreferences.getInt("connectedCount",0)
        val is_user = ApplicationClass.sSharedPreferences.getBoolean("is_user",false)

        //여기서 이제 팔로우 팔로잉 구분 .

        if(is_user){
            UsertabLayoutControl(connectedCount,followerCount,followingCount)
        }else{
            tabLayoutControl(followerCount,followingCount)
        }
    }

    private fun tabLayoutControl(follower: Int,following: Int){
        binding.vpFollowing.apply {
            adapter = FollowTabPageAdapter(this@FollowFragment)
        }

        TabLayoutMediator(binding.tabLayoutFollowing,binding.vpFollowing){ tab, position ->
            when(position){
                0 -> tab.text = "팔로워 ${follower}명"
                1 -> tab.text = "팔로잉 ${following}명"
                2 -> tab.text = "추천"
            }
        }.attach()
    }

    private fun UsertabLayoutControl(connected : Int,follower : Int, following :Int){

        binding.vpFollowing.apply {
            adapter = UserFollowTabPageAdapter(this@FollowFragment)
        }

        TabLayoutMediator(binding.tabLayoutFollowing,binding.vpFollowing){ tab, position ->
            when(position){
                0 -> tab.text = "함께 아는 친구 ${connected}명"
                1 -> tab.text = "팔로워 ${follower}명"
                2 -> tab.text = "팔로잉 ${following}명"
            }
        }.attach()
    }

    override fun onResume() {
        super.onResume()
        val mainAct = activity as MainActivity
        mainAct.hideNavigationBar(true)
    }

    override fun onGetFollowFollowerSuccess(response: GetFollowFollowersResponse) {
    }

    override fun onGetFollowFollwerFailure(message: String) {
        showCustomToast("오류 : $message")
    }

    override fun onPostDoFollowingSuccess(response: PostDoFollowingResponse) {
    }

    override fun onPostDoFollowingFailure(message: String) {
    }

    override fun onPostUnFollowingSuccess(response: PatchUnFollowingResponse) {
    }

    override fun onPostUnFollowingFailure(message: String) {
    }

    override fun onGetFollowFollowingSuccess(response: GetFollowFollowingResponse) {}

    override fun onGetFollowFollowingFailure(message: String) {}

    override fun onGetFollowAnotherSuccess(response: GetFollowAnotherResponse) {}

    override fun onGetFollowAnotherFailure(message: String) {}
}