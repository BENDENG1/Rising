package com.softsquared.template.kotlin.src.main.reels

import android.os.Bundle
import android.view.View
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.BaseFragment
import com.softsquared.template.kotlin.databinding.FragmentHomeBinding
import com.softsquared.template.kotlin.databinding.FragmentReelsBinding
import com.softsquared.template.kotlin.src.main.home.HomeFragmentInterface

class ReelsFragment : BaseFragment<FragmentReelsBinding>(FragmentReelsBinding::bind, R.layout.fragment_reels){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}