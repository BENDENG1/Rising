package com.softsquared.template.kotlin.src.main

import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.BaseActivity
import com.softsquared.template.kotlin.databinding.ActivityMainBinding
import com.softsquared.template.kotlin.src.main.home.HomeFragment
import com.softsquared.template.kotlin.src.main.list.ListFragment
import com.softsquared.template.kotlin.src.main.myPage.MyPageFragment

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    var imm : InputMethodManager? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager.beginTransaction().replace(R.id.main_frm, HomeFragment()).commitAllowingStateLoss()

        binding.mainBtmNav.run {
            setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.menu_main_btm_nav_home -> {
                        changeFragment(HomeFragment())
                    }
                    R.id.menu_main_btm_nav_list ->{
                        changeFragment(ListFragment())
                    }
                    R.id.menu_main_btm_nav_my_page -> {
                        changeFragment(MyPageFragment())
                    }
                }
                true
            }
            selectedItemId = R.id.menu_main_btm_nav_home
        }
    }

    private fun changeFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().replace(binding.mainFrm.id,fragment).commitAllowingStateLoss()
    }

    fun hideNavigationBar(hide : Boolean){
        if(hide){
            binding.mainBtmNav.visibility = View.GONE
        }else{
            binding.mainBtmNav.visibility = View.VISIBLE
        }
    }

    fun hideKeyboard(v : View){
        if(v != null){
            imm?.hideSoftInputFromWindow(v.windowToken,0)
        }
    }
    fun showKeyboard(v : View){
        imm?.showSoftInput(v,0)
    }
}