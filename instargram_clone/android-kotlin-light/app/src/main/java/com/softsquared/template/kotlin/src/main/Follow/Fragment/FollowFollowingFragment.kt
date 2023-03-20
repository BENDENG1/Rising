package com.softsquared.template.kotlin.src.main.Follow.Fragment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.config.BaseFragment
import com.softsquared.template.kotlin.databinding.FollowFollowingListBinding
import com.softsquared.template.kotlin.databinding.FragmentFollowFollowingBinding
import com.softsquared.template.kotlin.src.main.Follow.FollowFragmentInterface
import com.softsquared.template.kotlin.src.main.Follow.FollowService
import com.softsquared.template.kotlin.src.main.Follow.List.FollowFollowingAdapter
import com.softsquared.template.kotlin.src.main.Follow.models.*

class FollowFollowingFragment : BaseFragment<FragmentFollowFollowingBinding>(FragmentFollowFollowingBinding::bind
    , R.layout.fragment_follow_following),FollowFragmentInterface{

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val is_user = ApplicationClass.sSharedPreferences.getBoolean("is_user",false)
        val myId= ApplicationClass.sSharedPreferences.getInt("my_id",0)
        val user_id = ApplicationClass.sSharedPreferences.getInt("user_id",0)
        if(is_user){
            FollowService(this).tryGetFollowFollowing(user_id)
        }else{
            FollowService(this).tryGetFollowFollowing(myId)
        }

    }

    override fun onGetFollowFollowingSuccess(response: GetFollowFollowingResponse) {
        binding.rvFollowFollowing.apply {
            adapter = FollowFollowingAdapter(response.followFollowingResult.followings, object : FollowFollowingAdapter.FollowingClickListner<Following> {
                    override fun onViewClick(view: FollowFollowingListBinding, pos: Int) {
                        view.btnFollowingFollow.setOnClickListener {
                            view.btnFollowingFollow
                        }
                    }
                })
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        }
    }

    override fun onGetFollowFollowingFailure(message: String) {
        showCustomToast("오류 : $message")
    }

    override fun onGetFollowFollowerSuccess(response: GetFollowFollowersResponse) {}

    override fun onGetFollowFollwerFailure(message: String) {}

    override fun onGetFollowAnotherSuccess(response: GetFollowAnotherResponse) {}

    override fun onGetFollowAnotherFailure(message: String) {}

    override fun onPostDoFollowingSuccess(response: PostDoFollowingResponse) {}

    override fun onPostDoFollowingFailure(message: String) {}

    override fun onPostUnFollowingSuccess(response: PatchUnFollowingResponse) {}

    override fun onPostUnFollowingFailure(message: String) {}
}