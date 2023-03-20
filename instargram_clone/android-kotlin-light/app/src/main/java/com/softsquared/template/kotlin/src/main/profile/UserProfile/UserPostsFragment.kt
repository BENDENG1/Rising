package com.softsquared.template.kotlin.src.main.profile.UserProfile

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.config.BaseFragment
import com.softsquared.template.kotlin.databinding.FragmentUserPostsBinding
import com.softsquared.template.kotlin.databinding.UserpostslistBinding
import com.softsquared.template.kotlin.src.main.MainActivity
import com.softsquared.template.kotlin.src.main.profile.ProfilePartInterface
import com.softsquared.template.kotlin.src.main.profile.ProfileService
import com.softsquared.template.kotlin.src.main.profile.models.GetProfilePostResponse
import com.softsquared.template.kotlin.src.main.profile.models.GetProfileResponse
import com.softsquared.template.kotlin.src.main.profile.models.ProfilePostResult
import com.softsquared.template.kotlin.src.main.profile.models.ProfileResult

class UserPostsFragment : BaseFragment<FragmentUserPostsBinding>(FragmentUserPostsBinding::bind, R.layout.fragment_user_posts)
    , ProfilePartInterface {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val user_id = ApplicationClass.sSharedPreferences.getInt("user_id",0)
        ProfileService(this).tryGetProfilePost(user_id = user_id)

        ApplicationClass.sSharedPreferences.edit().putBoolean("is_user",true).apply()
    }

    override fun onResume() {
        super.onResume()
        val mainAct = activity as MainActivity
        mainAct.hideNavigationBar(false)
    }


    override fun onGetProfileSuccess(response: GetProfileResponse) {}
    override fun onGetProfileFailure(message: String) {}

    override fun onGetProfilePostSuccess(response: GetProfilePostResponse) {
        Log.d("testtesttest",response.isSuccess.toString())
        Log.d("testtesttest",response.message.toString())
        Log.d("testtesttest",response.code.toString())


        binding.rvUserPosts.apply {
            adapter = UserPostsAdapter(response.profilePostResult,object :UserPostsAdapter.UserPostsClickListener<ProfilePostResult>{
                override fun onViewClick(view: UserpostslistBinding, pos: Int) {

                }
            })
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        }
    }

    override fun onGetProfilePostFailure(message: String) {
        showCustomToast("오류 : $message")
    }
}