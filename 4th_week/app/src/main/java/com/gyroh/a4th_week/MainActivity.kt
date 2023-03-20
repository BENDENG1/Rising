package com.gyroh.a4th_week

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import com.gyroh.a4th_week.databinding.ActivityMainBinding
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    val binding by lazy {ActivityMainBinding.inflate(layoutInflater)}


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        with(binding){
            ibHowToPlay.setOnClickListener {
                rlHowToPlay.visibility = View.VISIBLE
                ivHome.setOnClickListener {
                    rlHowToPlay.visibility = View.GONE
                }
            }
            ibtnHowGameStart.setOnClickListener{
                val gameIntent = Intent(this@MainActivity,GameActivity::class.java)
                startActivity(gameIntent)
                finish()
            }
            ibGameStart.setOnClickListener{
                val intent = Intent(this@MainActivity,GameActivity::class.java)
                startActivity(intent)
                finish()
            }
        }





    }
}