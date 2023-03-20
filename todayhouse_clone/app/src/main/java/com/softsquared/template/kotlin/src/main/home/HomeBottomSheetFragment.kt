package com.softsquared.template.kotlin.src.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.softsquared.template.kotlin.databinding.FragmentHomeBottomsheetdialogBinding

class HomeBottomSheetFragment : BottomSheetDialogFragment() {

    private val binding by lazy {FragmentHomeBottomsheetdialogBinding.inflate(layoutInflater)}

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ibtnHomeBsdClose.setOnClickListener {
            parentFragmentManager.beginTransaction().remove(this@HomeBottomSheetFragment).commit()
        }

        //여기다가 이제 bottomsheet 범위별로 클릭했을때 전환 만들어야함

    }
}

//class HomeBottomSheetFragment : BaseFragment<FragmentHomeBottomsheetdialogBinding>
//    (FragmentHomeBottomsheetdialogBinding::bind , R.layout.fragment_home_bottomsheetdialog) {
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        binding.ibtnHomeBsdClose.setOnClickListener {
//            parentFragmentManager.beginTransaction().remove(this@HomeBottomSheetFragment)
//        }
//    }
//}