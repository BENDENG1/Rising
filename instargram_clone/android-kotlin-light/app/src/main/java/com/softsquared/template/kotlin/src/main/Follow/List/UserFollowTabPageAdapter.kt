package com.softsquared.template.kotlin.src.main.Follow.List

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.softsquared.template.kotlin.src.main.Follow.Fragment.FollowAnotherFragment
import com.softsquared.template.kotlin.src.main.Follow.Fragment.FollowFollowerFragment
import com.softsquared.template.kotlin.src.main.Follow.Fragment.FollowFollowingFragment
import com.softsquared.template.kotlin.src.main.Follow.Fragment.FollowFragment

class UserFollowTabPageAdapter(fragmentActivity: FollowFragment)
    : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> FollowAnotherFragment()
            1 -> FollowFollowerFragment()
            2 -> FollowFollowingFragment()
            else -> FollowFollowerFragment()
        }
    }
}