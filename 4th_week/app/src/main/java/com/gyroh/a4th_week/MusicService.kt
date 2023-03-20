package com.gyroh.a4th_week

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder

class MusicService : Service() {

    lateinit var musicPlayer : MediaPlayer

    override fun onBind(intent: Intent): IBinder {
        //Service의 객체와 통신할때 사용
        TODO("Return the communication channel to the service.")
    }
    //1회 호출
    override fun onCreate() {
        super.onCreate()
        musicPlayer = MediaPlayer.create(this,R.raw.cookingmusic)
        musicPlayer.isLooping = true
    }

    //서비스 호출 -> 음악재생
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        musicPlayer.start()
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        musicPlayer.stop()
    }

}