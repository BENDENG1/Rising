package com.gyroh.a3rd_week.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gyroh.a3rd_week.R
import com.gyroh.a3rd_week.databinding.FragmentChattingBinding
import com.gyroh.a3rd_week.databinding.FragmentViewBinding

class viewFragment : Fragment() {

    val binding by lazy {FragmentViewBinding.inflate(layoutInflater)}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }
}