package com.example.a2nd_week.Navigation

import android.os.Bundle
import android.text.Layout
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.a2nd_week.databinding.FragmentSubscribeBinding


class FragmentSubscribe : Fragment() {

    val binding by lazy {FragmentSubscribeBinding.inflate(layoutInflater)}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }
}