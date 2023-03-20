package com.softsquared.template.kotlin.src.main.browse

import android.os.Bundle
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.BaseFragment
import com.softsquared.template.kotlin.databinding.FragmentBrowseBinding

class BrowseFragment : BaseFragment<FragmentBrowseBinding>(FragmentBrowseBinding::bind, R.layout.fragment_browse) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//
//
//
//        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
//            override fun onTabSelected(tab: TabLayout.Tab?) {
//                //탭이 선택되었을때
//            }
//
//            override fun onTabUnselected(tab: TabLayout.Tab?) {
//                //탭이 선택되지 않은 상태로 변경되었을때
//            }
//
//            override fun onTabReselected(tab: TabLayout.Tab?) {
//                //이미 탭이 다시 선택
//            }
//        })
//
//        binding.vp2BrowseContainer.adapter = BrowseViewPager2Adapter(this)
//
//        TabLayoutMediator(binding.tabLayout,binding.vp2BrowseContainer){ tab, position ->
//            when(position){
//                0 -> tab.text = "팔로잉"
//                1 -> tab.text = "사진"
//                2 -> tab.text = "집들이"
//                3 -> tab.text = "노하우"
//                4 -> tab.text = "전문가집들이"
//                5 -> tab.text = "질문과답변"
//            }
//        }.attach()
    }


}