package com.softsquared.template.kotlin.src.main.list

import android.os.Bundle
import android.view.View
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.BaseFragment
import com.softsquared.template.kotlin.databinding.FragmentListBinding
import com.softsquared.template.kotlin.src.main.MainActivity
import com.softsquared.template.kotlin.src.main.home.HomeFragment

class ListFragment : BaseFragment<FragmentListBinding>(FragmentListBinding::bind , R.layout.fragment_list), ListFragmentInterface{

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.btnListDiagnosis.setOnClickListener{
            parentFragmentManager.beginTransaction().replace(R.id.main_frm,HomeFragment()).commit()
            val mainAct = activity as MainActivity
            mainAct.binding.mainBtmNav.selectedItemId = R.id.menu_main_btm_nav_home
        }
    }
}