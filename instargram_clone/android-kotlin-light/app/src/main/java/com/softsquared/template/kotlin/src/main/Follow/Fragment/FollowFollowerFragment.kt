package com.softsquared.template.kotlin.src.main.Follow.Fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.config.BaseFragment
import com.softsquared.template.kotlin.databinding.FollowFollowerListBinding
import com.softsquared.template.kotlin.databinding.FragmentFollowFollowersBinding
import com.softsquared.template.kotlin.src.main.Follow.FollowFragmentInterface
import com.softsquared.template.kotlin.src.main.Follow.FollowService
import com.softsquared.template.kotlin.src.main.Follow.List.FollowFollowerAdapter
import com.softsquared.template.kotlin.src.main.Follow.models.*

class FollowFollowerFragment : BaseFragment<FragmentFollowFollowersBinding>(FragmentFollowFollowersBinding::bind
    , R.layout.fragment_follow_followers),FollowFragmentInterface {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val is_user = ApplicationClass.sSharedPreferences.getBoolean("is_user",false)
        val myId= ApplicationClass.sSharedPreferences.getInt("my_id",0)
        Log.d("--------",myId.toString())
        val user_id = ApplicationClass.sSharedPreferences.getInt("user_id",0)
        if(is_user){
            FollowService(this).tryGetFollowFollowers(user_id)
        }else{
            FollowService(this).tryGetFollowFollowers(myId)
        }
    }

    override fun onGetFollowFollowerSuccess(response: GetFollowFollowersResponse) {
        binding.rvFollowFollowers.apply {
            adapter = FollowFollowerAdapter(response.followFollowerResult.follower, object : FollowFollowerAdapter.FollowClickListener<Follower>{
                override fun onViewClick(view: FollowFollowerListBinding, pos: Int) {
                    view.btnFollowersFollow.setOnClickListener {

                    }
                }
            })
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        }
    }

    override fun onGetFollowFollwerFailure(message: String) {
        showCustomToast("오류 : $message")
    }

    override fun onPostDoFollowingSuccess(response: PostDoFollowingResponse) {}

    override fun onPostDoFollowingFailure(message: String) {}

    override fun onPostUnFollowingSuccess(response: PatchUnFollowingResponse) {}

    override fun onPostUnFollowingFailure(message: String) {}

    override fun onGetFollowFollowingSuccess(response: GetFollowFollowingResponse) {}

    override fun onGetFollowFollowingFailure(message: String) {}

    override fun onGetFollowAnotherSuccess(response: GetFollowAnotherResponse) {}

    override fun onGetFollowAnotherFailure(message: String) {}
}