package com.example.a2nd_week.Navigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.a2nd_week.databinding.FragmentShortsBinding


class FragmentShorts : Fragment() {

    val binding by lazy {FragmentShortsBinding.inflate(layoutInflater)}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return binding.root
    }

}