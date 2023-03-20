package com.example.a2nd_week

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore.Audio.Media
import android.util.Log
import android.widget.MediaController
import com.example.a2nd_week.databinding.ActivityMainBinding
import com.example.a2nd_week.databinding.ActivityShortsBinding

class ShortsActivity : AppCompatActivity() {

    val TAG = "쇼츠 생명주기"
    val binding by lazy {ActivityShortsBinding.inflate(layoutInflater)}
    val SHORT_URI = "https://www.youtube.com/shorts/teG5eM_kwyU"
//    val SHORT_PATH = "android.resource://" + packageName + "/" + R.raw.pigeon
    val SHORT_PATH : String by lazy{"android.resource://" + packageName + "/" + R.raw.pigeon}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.bnvMain.itemIconTintList = null //null로 해줌으로써 화면에 보이게 설정

        Log.d(TAG,"onCreate()")

        var uri : Uri = Uri.parse(SHORT_PATH)
        with(binding){
            videoView.apply {
                setVideoURI(uri)
                setMediaController((MediaController(this@ShortsActivity)))
                requestFocus()
            }

        }
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