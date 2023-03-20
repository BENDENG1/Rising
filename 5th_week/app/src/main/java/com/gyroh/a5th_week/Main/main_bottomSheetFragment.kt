package com.gyroh.a5th_week.Main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.gyroh.a5th_week.R
import com.gyroh.a5th_week.databinding.FragmentMainBottomSheetBinding


class main_bottomSheetFragment : BottomSheetDialogFragment() {

    val binding by lazy {FragmentMainBottomSheetBinding.inflate(layoutInflater)}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding.ibtnDismiss.setOnClickListener {
            dismiss()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}