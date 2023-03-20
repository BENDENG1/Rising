package com.gyroh.a3rd_week.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gyroh.a3rd_week.R
import com.gyroh.a3rd_week.databinding.FragmentChattingBinding
import com.gyroh.a3rd_week.databinding.FragmentMoreBinding

class moreFragment : Fragment() {

    val binding by lazy { FragmentMoreBinding.inflate(layoutInflater)}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return binding.root
    }

}