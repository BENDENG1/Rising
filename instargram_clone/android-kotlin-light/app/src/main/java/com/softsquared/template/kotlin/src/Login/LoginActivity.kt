package com.softsquared.template.kotlin.src.Login

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.softsquared.template.kotlin.config.BaseActivity
import com.softsquared.template.kotlin.databinding.ActivityLoginBinding
import com.softsquared.template.kotlin.src.Login.models.PostLoginResponse
import com.softsquared.template.kotlin.src.main.MainActivity
import com.softsquared.template.kotlin.config.ApplicationClass.Companion.sSharedPreferences
import com.softsquared.template.kotlin.config.ApplicationClass.Companion.X_ACCESS_TOKEN
import com.softsquared.template.kotlin.src.Login.models.PostJoinResponse
import com.softsquared.template.kotlin.src.Login.models.PostLoginRequest
import com.softsquared.template.kotlin.util.ErrorDialog

class LoginActivity :BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate)
    ,LoginPartInterface{

//    private lateinit var loginID :String
//    private lateinit var loginPassword : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.btnLoginClick.setOnClickListener {
            //이제 onpost뭐 그런걸 해서 통신 해야함
            //되면 -> jwt -> sharedpref에 저장해서 다음번 통신 슈루룩하기 && finish()

            //일단 임시용 -> 나중에 jwt되면 연동하길
            val loginID = binding.tiedtLoginId.text.toString()
            val loginPassword = binding.tiedtLoginPassword.text.toString()
            val postRequest = PostLoginRequest(loginID,loginPassword)
            showLoadingDialog(context = this)
            LoginService(this).tryPostLogin(postRequest)
        }

        //회원가입 창으로 이동
        binding.btnLoginJoin.setOnClickListener {
            startActivity(Intent(this,JoinActivity::class.java))
        }
    }

    @SuppressLint("CommitPrefEdits")
    override fun onPostLoginSuccess(response: PostLoginResponse) {
        val sharedPref = sSharedPreferences.edit()
        dismissLoadingDialog()
        if(response.isSuccess){
            if(response.code == 1000){
                sharedPref.putString(X_ACCESS_TOKEN,response.postLoginResult.jwt)
                sharedPref.putInt("user_id",response.postLoginResult.user_id)
                sharedPref.putInt("my_id",response.postLoginResult.user_id)
                sharedPref.apply()
                startActivity(Intent(this,MainActivity::class.java))
                finish()
            }
        }
        else{
            sharedPref.putString("errorMessage",response.message)
            sharedPref.putString("errorType","로그인 할 수 없음")
            sharedPref.apply()
            ErrorDialog(this).show()
        }
    }

    override fun onPostLoginFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("로그인 실패 사유 : $message")
    }

    override fun onPostJoinSuccess(response: PostJoinResponse) {}

    override fun onPostJoinFailure(message: String) {}
}