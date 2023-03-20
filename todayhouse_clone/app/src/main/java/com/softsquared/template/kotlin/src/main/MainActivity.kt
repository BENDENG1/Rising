package com.softsquared.template.kotlin.src.main

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.BaseActivity
import com.softsquared.template.kotlin.databinding.ActivityMainBinding
import com.softsquared.template.kotlin.src.main.browse.BrowseFragment
import com.softsquared.template.kotlin.src.main.home.HomeFragment
import com.softsquared.template.kotlin.src.main.myPage.MyPageFragment
import com.softsquared.template.kotlin.src.main.repair.RepairFragment
import com.softsquared.template.kotlin.src.main.shopping.ShoppingFragment

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    var productNum : Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //supportFragmentManager.beginTransaction().replace(R.id.main_frm, HomeFragment()).commitAllowingStateLoss()


        binding.mainBtmNav.run {
            setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.menu_main_btm_nav_home -> {
                        changeFragment(HomeFragment())
                    }
                    R.id.menu_main_btm_nav_browse -> {
                        changeFragment(BrowseFragment())
                    }
                    R.id.menu_main_btm_nav_shopping ->{
                        changeFragment(ShoppingFragment())
                    }
                    R.id.menu_main_btm_nav_repair ->{
                        changeFragment(RepairFragment())
                    }
                    R.id.menu_main_btm_nav_mypage -> {
                        changeFragment(MyPageFragment())
                    }
                }
                true
            }
            selectedItemId = R.id.menu_main_btm_nav_home
        }
    }


    override fun onResume() {
        super.onResume()
        bottomNavigationHide(false)
    }

    private fun changeFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().replace(binding.mainFrm.id,fragment).commitAllowingStateLoss()
    }


    //바텀네비게이션 보여짐 유무
    fun bottomNavigationHide(state: Boolean){
        if(state){
            binding.mainBtmNav.visibility = View.GONE
        }else{
            binding.mainBtmNav.visibility = View.VISIBLE
        }
    }

}