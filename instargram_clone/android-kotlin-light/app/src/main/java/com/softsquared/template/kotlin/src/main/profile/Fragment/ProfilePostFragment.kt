package com.softsquared.template.kotlin.src.main.profile.Fragment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.config.ApplicationClass.Companion.sSharedPreferences
import com.softsquared.template.kotlin.config.BaseFragment
import com.softsquared.template.kotlin.databinding.FragmentProfilePostBinding
import com.softsquared.template.kotlin.databinding.ProfilePostListBinding
import com.softsquared.template.kotlin.src.main.MainActivity
import com.softsquared.template.kotlin.src.main.profile.List.ProfilePostAdapter
import com.softsquared.template.kotlin.src.main.profile.ProfilePartInterface
import com.softsquared.template.kotlin.src.main.profile.ProfileService
import com.softsquared.template.kotlin.src.main.profile.UserProfile.UserPostsFragment
import com.softsquared.template.kotlin.src.main.profile.models.GetProfilePostResponse
import com.softsquared.template.kotlin.src.main.profile.models.GetProfileResponse
import com.softsquared.template.kotlin.src.main.profile.models.ProfilePostResult
import com.softsquared.template.kotlin.util.GridSpacingItemDecoration

class ProfilePostFragment : BaseFragment<FragmentProfilePostBinding>(FragmentProfilePostBinding::bind, R.layout.fragment_profile_post)
    ,ProfilePartInterface{


    val user_id = sSharedPreferences.getInt("user_id",0)
    val my_id = sSharedPreferences.getInt("my_id",0)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        val isUser = ApplicationClass.sSharedPreferences.getBoolean("is_user",false)
        if(isUser){
            ProfileService(this).tryGetProfilePost(user_id)
        }else{
            ProfileService(this).tryGetProfile(my_id)
        }


    }


    override fun onGetProfileSuccess(response: GetProfileResponse) {
    }

    override fun onGetProfileFailure(message: String) {
    }

    override fun onGetProfilePostSuccess(response: GetProfilePostResponse) {
        binding.rvProfilePost.apply {
            adapter = ProfilePostAdapter(response.profilePostResult, object : ProfilePostAdapter.PostClickListener<ProfilePostResult>{
                override fun onViewClick(view: ProfilePostListBinding, post: Int) {
                    val mainAct = activity as MainActivity
                    mainAct.switchToFragment(UserPostsFragment())
//                    parentFragmentManager.beginTransaction().add(R.id.main_frm, UserPostsFragment(), "posts").addToBackStack("posts").commit()
                }
            })
            layoutManager = GridLayoutManager(context,3)
            addItemDecoration(GridSpacingItemDecoration(5,3))
        }
    }

    override fun onGetProfilePostFailure(message: String) {
    }
}