package com.gyroh.a4th_week

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.gyroh.a4th_week.databinding.ActivityResultBinding
import org.json.JSONArray

class ResultActivity : AppCompatActivity() {

    val binding by lazy {ActivityResultBinding.inflate(layoutInflater)}


    companion object {
        const val RANKING: String = "Ranking"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //putRankingTest() //점수 갱신 test용





    }

    override fun onStart(){
        super.onStart()
        uploadRanking()
    }
    override fun onResume() {
        super.onResume()
        transformActivity()
    }

    override fun onStop(){
        super.onStop()

    }

    private fun transformActivity() {
        binding.btnAgain.setOnClickListener {
            val intent = Intent(this@ResultActivity, GameActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.btnHome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun uploadRanking(){
        val sharedpref = getSharedPreferences(RANKING,MODE_PRIVATE)
        val editor = sharedpref.edit()

        val score = intent.getIntExtra("Score",0)
        val ranking = arrayListOf<Int>()

        for(i : Int in 0 until 5){
            ranking.add(i,sharedpref.getInt("${i+1}", -2))
        }

        ranking.add(5, score)
        ranking.sortDescending() //내림차순
        for(i in 0 until 5){
            editor.putInt("${i+1}",ranking[i])
            editor.commit()
        }

        binding.tvFirstScore.text = "${ranking[0]}"
        binding.tvSecondScore.text = "${ranking[1]}"
        binding.tvThirdScore.text = "${ranking[2]}"
        binding.tvFourthScore.text = "${ranking[3]}"
        binding.tvFifthScore.text = "${ranking[4]}"

    }

    //필요할때마다 점수 대신 업로드가 필요없지 어차피 default갱신인데
//    private fun putRankingTest(){
//        val sharedpref = getSharedPreferences(RANKING, MODE_PRIVATE)
//        val editor = sharedpref.edit()
//
//        for(i in 0 until 5){
//            editor.putInt("i",-100)
//        }
//    }

}