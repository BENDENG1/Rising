package com.gyroh.a4th_week

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageButton
import android.widget.ImageView
import com.gyroh.a4th_week.databinding.ActivityGameBinding

class GameActivity : AppCompatActivity() {

    companion object{
        const val BREADTIME : Int = 10
        const val THREADSLEEP : Long= 1000
        val TIMESECTOR = arrayOf(50,40,30,20,10)
    }

    private val binding by lazy {ActivityGameBinding.inflate(layoutInflater)}
    var gameRunning : Boolean = true //게임이 작동중인지
    var limitTime : Int = 60 //게임시간 60초
    var musicOn : Boolean = true
    //0:도우, 1:우유, 2:계란, 3:뒤집기, 4:손
    var itemSelect: BooleanArray = BooleanArray(5) { false } //enumclass수정 요망 ->
    var totalScore : Int= 0
    var breadCount : Int= 0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //초기에 한번만 설정하고 값들에 대해서는 수정 해야하므로 onCreate()
        binding.ibtnDough.isSelected = true
        itemSelect[0] = true


        gameStart() //onStart에 놓으면 계속 intent넘어가고 돌아오면 중첩되서 안됨
        timerControlAnimation() //타이머 커스텀으로 만들어서 시간에 따라 쓰레드 적용
        breadThread() //빵을 만드는 한틀마다 쓰레드 각각 작동
        customerorder() //주문이 들어와서 처리하는 과정



    }

    //주문이 들어오면 처리하는 과정
    private fun customerorder(){
        var numberOrder : Int = 0
        var isOrder : Boolean = false
        val breadList: Array<ImageView> = arrayOf(binding.ivBread1,binding.ivBread2,binding.ivBread3,binding.ivBread4
            ,binding.ivBread5,binding.ivBread6,binding.ivBread7,binding.ivBread8,binding.ivBread9,binding.ivBread10)


        Thread(){
            while(limitTime >=0){
                if(gameRunning){
                    if(!isOrder){
                        numberOrder  = (1..8).random() // 1~8 갯수 랜덤으로 만들어짐
                        runOnUiThread {
                            binding.tvOrderCount.text = "$numberOrder"
                        }
                        isOrder = !isOrder
                        val orderIntent = Intent(this@GameActivity,OrderService::class.java)
                        startService(orderIntent)
                    }
                    if(isOrder){
                        if (itemSelect[4]) {
                            itemSelect[4] = false
                            if (breadCount >= numberOrder) {
                                val finishService = Intent(this@GameActivity, FinishService::class.java)
                                startService(finishService)
                                breadCount -= numberOrder
                                totalScore += numberOrder * 10
                                isOrder = !isOrder
                                runOnUiThread {
                                    for(i in 0 until 8){
                                        if(i < breadCount){
                                            breadList[i].visibility = View.VISIBLE
                                        }
                                        else
                                            breadList[i].visibility = View.GONE
                                    }
                                    binding.tvScore.text = totalScore.toString()
                                }
                            }
                        }
                    }
                }
                Thread.sleep(1000)
            }

        }.start()
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
        binding.ibtnPause.setOnClickListener { gamePause() }
        binding.ibResume.setOnClickListener { gameResume() }
        itemClick()
        musicControl()
    }

    override fun onPause() {
        super.onPause()

        //게임을 나가게 되거나 다른 돌발상황에 대해서는 노래가 꺼져야함 onResume에 노래를 다시 시작하기 때문에 상관x
        val intent = Intent(this,MusicService::class.java)
        musicTurnOff(intent)

    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
    }


    private fun itemClick(){
        binding.ibtnDough.setOnClickListener { posItemSelect(binding.ibtnDough,0) }
        binding.ibtnMilk.setOnClickListener { posItemSelect(binding.ibtnMilk,1) }
        binding.ibtnEgg.setOnClickListener { posItemSelect(binding.ibtnEgg,2) }
        binding.ibtnTurn.setOnClickListener { posItemSelect(binding.ibtnTurn,3) }
        binding.ibtnHand.setOnClickListener { posItemSelect(binding.ibtnHand,4) }
    }

    private fun breadThread(){
        testThread(binding.ibtnBread1)
        testThread(binding.ibtnBread2)
        testThread(binding.ibtnBread3)
        testThread(binding.ibtnBread4)
        testThread(binding.ibtnBread5)
        testThread(binding.ibtnBread6)
        testThread(binding.ibtnBread7)
        testThread(binding.ibtnBread8)
    }


    private fun posItemSelect(imageButton: ImageButton, pos : Int){
        //맙소사 이런 혼종이 나오다니..
        var arr: Array<ImageButton> = arrayOf(binding.ibtnDough, binding.ibtnMilk,binding.ibtnEgg,binding.ibtnTurn,binding.ibtnHand)

        imageButton.setOnClickListener {
            itemSelect[pos] = !itemSelect[pos]
            imageButton.isSelected = itemSelect[pos]
            if(itemSelect[pos]){ //골라졌을때 selected될때
                for(i in 0..4){
                    if(i != pos){
                        itemSelect[i] = false
                        arr[i].isSelected = itemSelect[i]
                    }
                }
            }
        }
    }

    private fun testThread(breadState: ImageButton){
        Thread(){
            var cooking : Boolean = false
            var step = 1
            var time = 0
            val breadList: Array<ImageView> = arrayOf(binding.ivBread1,binding.ivBread2,binding.ivBread3,binding.ivBread4
                ,binding.ivBread5,binding.ivBread6,binding.ivBread7,binding.ivBread8,binding.ivBread9,binding.ivBread10)

            while(limitTime > 0 ){
                if(gameRunning){
                    breadState.setOnClickListener{
                        runOnUiThread {
                            when(step){
                                1 ->{
                                    if(itemSelect[0]){
                                        cooking = true //계란빵 만들기 시작
                                        breadState.setImageResource(R.drawable.ic_game_2)
                                        step+=1
                                        time =0
                                    }
                                }
                                2->{
                                    if(itemSelect[1]){
                                        breadState.setImageResource(R.drawable.ic_game_3)
                                        step+=1
                                        time =0
                                    }
                                }
                                3 ->{
                                    if(itemSelect[2]){
                                        breadState.setImageResource(R.drawable.ic_game_4)
                                        step+=1
                                        time =0
                                    }
                                }
                                4 ->{
                                    if(itemSelect[3]){
                                        breadState.setImageResource(R.drawable.ic_game_5)
                                        step+=1
                                        time =0
                                    }
                                }
                                5 ->{
                                    if(itemSelect[3]){
                                        breadState.setImageResource(R.drawable.ic_game_6)
                                        step+=1
                                        time =0
                                    }
                                }
                                6 ->{
                                    if(itemSelect[3]){
                                        breadState.setImageResource(R.drawable.ic_game_1)
                                        step = 1
                                        cooking = false //다 만들어진거니 cooking false
                                        breadCount += 1
                                    }
                                }
                                7 -> {
                                    breadState.setImageResource(R.drawable.ic_game_1)
                                    time = 0
                                    step = 1
                                    cooking = false
                                }
                            }
                            if(breadCount > 0){
                                breadList[breadCount-1].visibility = View.VISIBLE
                            }
                            /*binding.tvScore.text = totalScore.toString()*/
                        }//runonuithread
                    } //button.setonclicklistener
                    Thread.sleep(1000)
                    if(cooking) //과정을 진행중에만 시간을 카운팅
                        time += 1
                    if(time >= BREADTIME && cooking){
                        runOnUiThread {
                            breadState.setImageResource(R.drawable.ic_game_7fail)
                        }
                        step = 7
                        cooking = false
                    }
                } //ifgamerunning
            }
        }.start()
    }


    //백버튼 누를시 게임 종료(게임하는데 누가 눌러! 누르지마!)
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if(keyCode ==KeyEvent.KEYCODE_BACK)
            finish()
        return super.onKeyDown(keyCode, event)
    }



    //쓰레드 게임시작 , 타이머 조절함수
    private fun gameStart(){
        Thread(){
            while(limitTime > 0){
                runOnUiThread {
                    binding.tvTimer.text = limitTime.toString()
                }
                if(gameRunning){
                    limitTime -= 1
                    Thread.sleep(1000)
                }
            }
            if(limitTime == 0)
            {
                val musicIntent = Intent(this,MusicService::class.java)
                stopService(musicIntent)
                //stopService(Intent(this,OrderService::class.java))
                //stopService(Intent(this,FinishService::class.java))
                val resultIntent = Intent(this@GameActivity,ResultActivity::class.java)
                val score = binding.tvScore.text.toString().toInt()
                resultIntent.putExtra("Score",score)
                startActivity(resultIntent)
                finish()
            }
        }.start()
    }

    //음악 조절하는 함수 (주문접수랑 완료사운드도 포함)
    private fun musicControl(){
        val musicService = Intent(this@GameActivity,MusicService::class.java)
        musicTurnOn(musicService)
        binding.ibtnSound.setOnClickListener {
            if(musicOn){
                musicTurnOff(musicService)
            }else{
                musicTurnOn(musicService)
            }
        }
    }

    //음악 끄기
    private fun musicTurnOff(intent: Intent){
        stopService(intent)
        musicOn = false
        binding.ibtnSound.setImageResource((R.drawable.ic_musicoff))
    }

    //음악 틀기
    private fun musicTurnOn(intent: Intent){

        startService(intent)
        musicOn = true
        binding.ibtnSound.setImageResource(R.drawable.ic_musicon)
    }

    //타이머 컨트롤 하는데 시간이 점점 줄어들때마다 더욱 빠르게 움직이기 하기 위해 쓰레드 사용
    private fun timerControlAnimation(){
        Thread(){
            val timerAnimation = AnimationUtils.loadAnimation(this,R.anim.translate)
            with(binding){
                ivTimer.startAnimation(timerAnimation)
                while(limitTime > 0){
                    when (limitTime) {
                        in TIMESECTOR[1] until TIMESECTOR[0] -> {
                            timerAnimation.duration = 400
                            ivTimer.startAnimation(timerAnimation)
                        }
                        in TIMESECTOR[2] until TIMESECTOR[1] -> {
                            timerAnimation.duration = 300
                            ivTimer.startAnimation(timerAnimation)
                        }
                        in TIMESECTOR[3] until TIMESECTOR[2] -> {
                            timerAnimation.duration = 200
                            ivTimer.startAnimation(timerAnimation)
                        }
                        in TIMESECTOR[4] until TIMESECTOR[3] -> {
                            timerAnimation.duration = 100
                            ivTimer.startAnimation(timerAnimation)
                        }
                        in 0 until TIMESECTOR[4] -> {
                            timerAnimation.duration = 50
                            ivTimer.startAnimation(timerAnimation)
                        }
                    }
                    Thread.sleep(1000)
                }
            }
        }.start()
    }

    //게임 정지 될때 설정
    private fun gamePause(){
        gameRunning = false
        binding.ibResume.bringToFront() //맨앞으로 꺼냄
        binding.ibResume.visibility = View.VISIBLE
    }

    //게임 재개 될때 애니메이션, view, 값 변경 -> 애니메이션 빼는걸로 결정
    private fun gameResume() {
        binding.ibResume.visibility = View.GONE
        gameRunning = true
    }
}