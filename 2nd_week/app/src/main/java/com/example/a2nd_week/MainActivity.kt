package com.example.a2nd_week

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.example.a2nd_week.Navigation.*
import com.example.a2nd_week.databinding.ActivityMainBinding
import com.example.a2nd_week.databinding.ActivityMainBinding.inflate

class MainActivity : AppCompatActivity() {

    val binding by lazy {ActivityMainBinding.inflate(layoutInflater)}
    val TAG = "메인 생명주기"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        Log.d(TAG,"onCreate()")
        with(binding){
            bnvMain.itemIconTintList = null //null로 해줌으로써 화면에 보이게 설정


            imageButtonShorts1.setOnClickListener {
                val intentshorts = Intent(this@MainActivity,ShortsActivity::class.java)
                startActivity(intentshorts)
            }

            menuicon.setOnClickListener {
                val bottomSheet = BottomSheetFragment()
                bottomSheet.show(supportFragmentManager,bottomSheet.tag)
                onPause()
            }
        }
        moveFragment() //bottomnavigation 클릭시 fragment전환 함수

    }

    //프래그먼트 전환 함수
    private fun moveFragment(){
        with(binding){
            bnvMain.apply{
                setOnItemSelectedListener { item ->
                    when (item.itemId){
                        R.id.bottombar_home ->{
                            changeFragment(FragmentHome())
                            Log.d("프래그먼트","home")
                        }
                        R.id.bottombar_shorts -> {
                            changeFragment(FragmentShorts())
                            Log.d("프래그먼트","shorts")
                        }
                        R.id.bottombar_subscribe -> {
                            changeFragment(FragmentSubscribe())
                            Log.d("프래그먼트","subscribe")
                        }
                        R.id.bottombar_keep -> {
                            changeFragment(FragmentKeep())
                            Log.d("프래그먼트","keep")
                        }
                    }
                    true
                }
                selectedItemId = R.id.bottombar_home
            }
        }
    }

    //전환될때 필요한 함수
    private fun changeFragment(fragment: Fragment){

        supportFragmentManager
            .beginTransaction() //시작
            .replace(binding.bnFragment.id,fragment) // 프래그먼트 전환
            .commit() //끝
    }



    override fun onStart() {
        super.onStart()
        Log.d(TAG,"onStart()")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG,"onResume()")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG,"onPause()")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG,"onStop()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG,"onDestroy()")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG,"onRestart()")
    }
}