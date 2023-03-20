package com.softsquared.template.kotlin.src.main

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.BaseActivity
import com.softsquared.template.kotlin.databinding.ActivityMainBinding
import com.softsquared.template.kotlin.src.main.home.HomeFragment
import com.softsquared.template.kotlin.src.main.profile.Fragment.ProfileFragment
import com.softsquared.template.kotlin.src.main.reels.ReelsFragment
import com.softsquared.template.kotlin.src.main.search.fragment.SearchFragment
import com.softsquared.template.kotlin.src.main.upload.UploadFragment

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bottomNavigationBarControl()

    }

    private fun bottomNavigationBarControl(){
//        supportFragmentManager.beginTransaction().replace(R.id.main_frm, HomeFragment()).commitAllowingStateLoss()
        binding.mainBtmNav.run {
            setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.menu_main_btm_nav_home -> {
                        bottomNaviBgColor(true)
                        hideNavigationBar(false)
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_frm, HomeFragment())
                            .commitAllowingStateLoss()
                    }
                    R.id.menu_main_btm_nav_search -> {
                        bottomNaviBgColor(true)
                        hideNavigationBar(false)
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_frm, SearchFragment())
                            .commitAllowingStateLoss()
                    }
                    R.id.menu_main_btm_nav_upload -> {
                        bottomNaviBgColor(true)
                        hideNavigationBar(false)
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_frm, UploadFragment())
                            .commitAllowingStateLoss()
                    }
                    R.id.menu_main_btm_nav_reels -> {
                        bottomNaviBgColor(false)
                        hideNavigationBar(false)
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_frm, ReelsFragment())
                            .commitAllowingStateLoss()
                    }
                    R.id.menu_main_btm_nav_profile -> {
                        bottomNaviBgColor(true)
                        hideNavigationBar(false)
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_frm, ProfileFragment())
                            .commitAllowingStateLoss()
                    }
                }
                true
            }
            selectedItemId = R.id.menu_main_btm_nav_home
        }
    }

    fun bottomNaviBgColor(white: Boolean)
    {
        if(white)
        {
            binding.mainBtmNav.itemBackground = ContextCompat.getDrawable(this, R.color.white)
            binding.mainBtmNav.itemIconTintList = ContextCompat.getColorStateList(this,R.color.black)
        } else {
            binding.mainBtmNav.itemBackground = ContextCompat.getDrawable(this, R.color.black)
            binding.mainBtmNav.itemIconTintList = ContextCompat.getColorStateList(this,R.color.white)
        }
    }

    fun hideNavigationBar(hide : Boolean){
        if(hide){
            binding.mainBtmNav.visibility = View.GONE
        }else{
            binding.mainBtmNav.visibility = View.VISIBLE
        }
    }

    fun switchToFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, fragment)
            .addToBackStack(null)
            .commit()
    }

}