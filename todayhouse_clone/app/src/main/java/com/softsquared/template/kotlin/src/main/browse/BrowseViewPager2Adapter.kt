package com.softsquared.template.kotlin.src.main.browse

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class BrowseViewPager2Adapter(browseFragment: BrowseFragment) : FragmentStateAdapter(browseFragment) {

    //var fragments : ArrayList<Fragment> = ArrayList()

    override fun getItemCount(): Int {
        //return fragments.size
        return 6
    }

    override fun createFragment(position: Int): Fragment {
        //return fragments[position]
        return when(position){
            0 -> BrowseFollowingFragment()
            1 -> BrowsePictureFragment()
            2 -> BrowseHousesFragment()
            3 -> BrowseKnowHowFragment()
            4 -> BrowseExpertHousesFragment()
            else -> BrowseQNAFragment()
        }
    }

}