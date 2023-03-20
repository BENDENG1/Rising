package com.gyroh.a5th_week.Activity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.gyroh.a5th_week.Fragment.FragmentDiscountPlace
import com.gyroh.a5th_week.Fragment.FragmentNews
import com.gyroh.a5th_week.Fragment.FragmentPlus
import com.gyroh.a5th_week.Fragment.FragmentProfile
import com.gyroh.a5th_week.Main.FragmentFindPlace
import com.gyroh.a5th_week.R
import com.gyroh.a5th_week.databinding.ActivityMainBinding
import com.kakao.sdk.common.util.Utility


class MainActivity : AppCompatActivity() {

    interface onBackPressedListener{
        fun onBackPressed()
    }

    //api key : ce1da4d61ada4cad3f06d7faba3841c8 -> weather
    //api key : UPYKo30hO6I84E2Ds136lHQCPjNAyLx5ozwZH%2FSXQJSKj4f9%2FmzeveiVtPNhSabyqfP1Y0uZ7rQTpbu4lmXJZw%3D%3D
    val binding by lazy {ActivityMainBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        checkAccess()
        moveFragment()

    }

    //권한요청
    private fun checkAccess(){
        val permissionFine = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        val permissionCoarse = ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)
        if (permissionFine == PackageManager.PERMISSION_DENIED) { //위치 권한 확인
            //위치 권한 요청
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 0)
        }
        if(permissionCoarse == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),1)
        }
    }

    private fun moveFragment(){

        supportFragmentManager.beginTransaction().replace(binding.frameLayout.id,
            FragmentFindPlace()
        ).commit()

        binding.bottomNavi.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.frag_FindPlace ->{
                    changeFragment(FragmentFindPlace())
                }
                R.id.frag_DiscountPlace ->{
                    changeFragment(FragmentDiscountPlace())
                }
                R.id.frag_Plus ->{
                    changeFragment(FragmentPlus())
                }
                R.id.frag_News ->{
                    changeFragment(FragmentNews())
                }
                R.id.frag_Profile ->{
                    changeFragment(FragmentProfile())
                }
            }
            true
        }
    }

    private fun changeFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().replace(binding.frameLayout.id,fragment).commit()
    }

    override fun onBackPressed(){
        //해당 액티비티에서 띄운 프래그먼트에서 뒤로가기를 누르게되면 구현한 onBackPressed가 호출이 되는것이다.
        //interface로 Listener을 생성했기 때문
        val fragmentList = supportFragmentManager.fragments
        for(fragment in fragmentList){
            if(fragment is onBackPressedListener){
                (fragment as onBackPressedListener).onBackPressed()
                return
            }
        }
    }

    fun hideBottomNavigation(hide : Boolean){
        if(hide)
            binding.bottomNavi.visibility = View.GONE
        else
            binding.bottomNavi.visibility = View.VISIBLE
    }

}