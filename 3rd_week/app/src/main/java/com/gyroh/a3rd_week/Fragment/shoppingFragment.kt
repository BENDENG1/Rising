package com.gyroh.a3rd_week.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gyroh.a3rd_week.R
import com.gyroh.a3rd_week.databinding.FragmentShoppingBinding


class shoppingFragment : Fragment() {

    val binding by lazy {FragmentShoppingBinding.inflate(layoutInflater)}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

}