package com.softsquared.template.kotlin.src.Login

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.BaseActivity
import com.softsquared.template.kotlin.databinding.ActivityLoginBinding
import com.softsquared.template.kotlin.src.Login.models.PostJoinResponse
import com.softsquared.template.kotlin.src.Login.models.PostLoginResponse

class LoginActivity : BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate),LoginFragmentInterface{

//    val sharedPreferences: SharedPreferences = getSharedPreferences("Info", MODE_PRIVATE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setFragment(LoginFragment())

    }

    private fun setFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.login_frm,fragment).commit()
    }

    override fun onPostJoinSuccess(response: PostJoinResponse) {}

    override fun onPostJoinFailure(message: String) {}

    override fun onPostLoginSuccess(response: PostLoginResponse) {}

    override fun onPostLoginFailure(message: String) {}
}