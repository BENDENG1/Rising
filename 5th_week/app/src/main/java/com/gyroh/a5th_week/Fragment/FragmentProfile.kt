package com.gyroh.a5th_week.Fragment

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.gyroh.a5th_week.Activity.LoginActivity
import com.gyroh.a5th_week.databinding.FragmentProfileBinding
import com.kakao.sdk.user.UserApiClient
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL


class FragmentProfile : Fragment() {

    val binding by lazy { FragmentProfileBinding.inflate(layoutInflater) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val sharedPreferences = activity?.getSharedPreferences("profile", AppCompatActivity.MODE_PRIVATE)
        val isLogin = sharedPreferences?.getInt("isLogin",0)
        Log.d("1111",sharedPreferences?.getInt("isLogin",0).toString())
        //Log.d("1111", sharedPreferences?.getString("nickName","111").toString())
        //val nickname = sharedPreferences?.getString("nickName","nickName?")



        //로그인 및 사진 닉네임 활용
        UserApiClient.instance.me { user, error ->
            if(user != null){
                with(binding){
                    btnProfileLoginout.text = "로그아웃"
                    tvProfileLogin.visibility = View.GONE
                    tvProfileName.apply{
                        visibility = View.VISIBLE
                        text = user.kakaoAccount?.profile?.nickname
                    }
                    ibtnProfile.apply{
                        visibility = View.VISIBLE
                        Glide.with(this)
                            .load(user.kakaoAccount?.profile?.profileImageUrl)
                            .circleCrop() //오..
                            .into(this)
                    }
                }

            }
        }



        binding.btnProfileLoginout.setOnClickListener {
            if(isLogin == 1){
                UserApiClient.instance.logout { error ->
                    if (error != null){
                        Toast.makeText(context,"로그아웃 실패",Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(context,"로그아웃 성공",Toast.LENGTH_SHORT).show()
                    }
                }
            }
            //어차피 로그인이든 로그아웃이든 로그인 테마쪽으로 간다는것을 명시 하는?
            val intent = Intent(activity,LoginActivity::class.java)
            startActivity(intent)
        }

        UserApiClient.instance.logout { error ->
            if (error != null) {
                Toast.makeText(context, "로그아웃 실패", Toast.LENGTH_SHORT).show()
            }else {
                Toast.makeText(context, "로그아웃 성공", Toast.LENGTH_SHORT).show()
            }
        }


        return binding.root
    }

    private fun getBitmap(url: String?): Bitmap? {
        var imgUrl: URL? = null
        var connection: HttpURLConnection? = null
        var isInput: InputStream? = null
        var retBitmap: Bitmap? = null
        try {
            imgUrl = URL(url)
            connection = imgUrl.openConnection() as HttpURLConnection
            connection.setDoInput(true) //url로 input받는 flag 허용
            connection.connect() //연결
            isInput = connection.getInputStream() // get inputstream
            retBitmap = BitmapFactory.decodeStream(isInput)
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        } finally {
            connection?.disconnect()
            return retBitmap
        }
    }


}