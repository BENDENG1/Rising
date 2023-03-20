package com.softsquared.template.kotlin.src.main.profile.List

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.softsquared.template.kotlin.src.main.profile.Fragment.ProfileFragment
import com.softsquared.template.kotlin.src.main.profile.Fragment.ProfilePostFragment
import com.softsquared.template.kotlin.src.main.profile.Fragment.ProfileReelsFragment
import com.softsquared.template.kotlin.src.main.profile.Fragment.ProfileTagFragment

class ProfileTabPageAdapter(fragmentActivity: ProfileFragment) :
    FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ProfilePostFragment()
            1 -> ProfileReelsFragment()
            2 -> ProfileTagFragment()
            else -> ProfilePostFragment()
        }
    }
}