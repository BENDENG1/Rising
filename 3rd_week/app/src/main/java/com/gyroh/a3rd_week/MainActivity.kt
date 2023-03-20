package com.gyroh.a3rd_week

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.gyroh.a3rd_week.Fragment.*
import com.gyroh.a3rd_week.FriendsParts.AddIdActivity
import com.gyroh.a3rd_week.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy {ActivityMainBinding.inflate(layoutInflater)}


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)



        Log.d("----", "onCreate: OPEN")
        runFragment()
    }

    //바텀 네비게이션 프래그먼트 움직이는 함수
    private fun runFragment() {
        with(binding){
            bottomNavigationView.setOnItemSelectedListener { item ->
                when(item.itemId){
                    R.id.action_friends -> {
                        Log.d("test Fragment","친구창")
                        changeFragment(friendsFragment())
                    }
                    R.id.action_chatting ->{
                        Log.d("test Fragment","채팅창")
                        changeFragment(chattingFragment())
                    }
                    R.id.action_view ->{
                        Log.d("test Fragment","view")
                        changeFragment(viewFragment())
                    }
                    R.id.action_shopping -> {
                        Log.d("test Fragment","shopping")
                        changeFragment(shoppingFragment())
                    }
                    R.id.action_more -> {
                        Log.d("test Fragment","more")
                        changeFragment(moreFragment())
                    }
                }
                true
            }
            bottomNavigationView.selectedItemId = R.id.action_friends //앱을 처음 열었을때 친구창 메뉴 설정
        }

    }

    //프래그먼트 전환 메소드
    private fun changeFragment(fragment: Fragment){
        if(intent.getBundleExtra("Bundle") != null && fragment.context == friendsFragment().context) {
            fragment.arguments = intent.getBundleExtra("Bundle")
        }
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frameLayout,fragment)
            .commit()
    }
}