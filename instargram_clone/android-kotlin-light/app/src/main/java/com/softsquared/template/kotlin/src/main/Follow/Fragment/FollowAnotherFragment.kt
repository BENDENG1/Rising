package com.softsquared.template.kotlin.src.main.Follow.Fragment

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.config.BaseFragment
import com.softsquared.template.kotlin.databinding.FollowAnotherListBinding
import com.softsquared.template.kotlin.databinding.FragmentFollowAnotherBinding
import com.softsquared.template.kotlin.src.main.Follow.FollowFragmentInterface
import com.softsquared.template.kotlin.src.main.Follow.FollowService
import com.softsquared.template.kotlin.src.main.Follow.List.FollowAnotherAdapter
import com.softsquared.template.kotlin.src.main.Follow.models.*

class FollowAnotherFragment : BaseFragment<FragmentFollowAnotherBinding>(FragmentFollowAnotherBinding::bind
    ,R.layout.fragment_follow_another), FollowFragmentInterface {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val is_user = ApplicationClass.sSharedPreferences.getBoolean("is_user",false)
        val myId= ApplicationClass.sSharedPreferences.getInt("my_id",0)
        val user_id = ApplicationClass.sSharedPreferences.getInt("user_id",0)

        if(is_user){
            FollowService(this).tryGetFollowAnother(user_id)
        }else{
            FollowService(this).tryGetFollowAnother(myId)
        }
    }

    override fun onGetFollowAnotherSuccess(response: GetFollowAnotherResponse) {
        val my_id = ApplicationClass.sSharedPreferences.getInt("my_id",0)
        if(response.followAnotherResult.connected_follows.isNotEmpty())
        {
            binding.rvFollowAnother.apply {
                adapter = FollowAnotherAdapter(response.followAnotherResult.connected_follows, object : FollowAnotherAdapter.AnotherFollowClickListener<ConnectedFollow>{
                    override fun onViewClick(view: FollowAnotherListBinding, pos: Int) {
                        if(response.followAnotherResult.connected_follows[pos].follow_status ==1){
                            FollowService(this@FollowAnotherFragment).tryPatchUnFollowing(my_id,response.followAnotherResult.connected_follows[pos].user_id)
                            view.btnAnotherFollow.apply {
                                text = "팔로우"
                                setTextColor(ContextCompat.getColor(context, R.color.white))
                                setBackgroundResource(R.drawable.follow_border_blue_rectangle)
                            }
                        }
                        else if(response.followAnotherResult.connected_follows[pos].follow_status ==0){
                            FollowService(this@FollowAnotherFragment).tryPostDoFollowing(my_id,response.followAnotherResult.connected_follows[pos].user_id)
                            view.btnAnotherFollow.apply {
                                text = "팔로잉"
                                setTextColor(ContextCompat.getColor(context, R.color.black))
                                setBackgroundResource(R.drawable.follow_border_gray_rectangle)
                            }
                        }
                    }
                })
                layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
            }
        }
    }

    override fun onGetFollowAnotherFailure(message: String) {
        showCustomToast("오류 : $message")
    }


    override fun onGetFollowFollowerSuccess(response: GetFollowFollowersResponse) {}

    override fun onGetFollowFollwerFailure(message: String) {}

    override fun onGetFollowFollowingSuccess(response: GetFollowFollowingResponse) {}

    override fun onGetFollowFollowingFailure(message: String) {}



    override fun onPostDoFollowingSuccess(response: PostDoFollowingResponse) {}

    override fun onPostDoFollowingFailure(message: String) {}

    override fun onPostUnFollowingSuccess(response: PatchUnFollowingResponse) {}

    override fun onPostUnFollowingFailure(message: String) {}
}