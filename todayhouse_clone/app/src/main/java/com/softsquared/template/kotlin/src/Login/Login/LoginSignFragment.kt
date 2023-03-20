package com.softsquared.template.kotlin.src.Login.Login

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.ApplicationClass.Companion.X_ACCESS_TOKEN
import com.softsquared.template.kotlin.config.ApplicationClass.Companion.sSharedPreferences
import com.softsquared.template.kotlin.config.BaseFragment
import com.softsquared.template.kotlin.databinding.FragmentLoginSignBinding
import com.softsquared.template.kotlin.src.Login.LoginFragmentInterface
import com.softsquared.template.kotlin.src.Login.LoginService
import com.softsquared.template.kotlin.src.Login.models.PostJoinResponse
import com.softsquared.template.kotlin.src.Login.models.PostLoginRequest
import com.softsquared.template.kotlin.src.Login.models.PostLoginResponse
import com.softsquared.template.kotlin.src.main.MainActivity

class LoginSignFragment : BaseFragment<FragmentLoginSignBinding> (FragmentLoginSignBinding::bind, R.layout.fragment_login_sign)
    ,LoginFragmentInterface{
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ibtnSignBack.setOnClickListener {
            backFragment()
        }

        binding.btnSignLogin.setOnClickListener {
            val userEmail = binding.edtSignEmail.text.toString()
            val userPw = binding.edtSignPassword.text.toString()
            val postRequest = PostLoginRequest(userEmail, userPw)
            showLoadingDialog(requireContext())
            LoginService(this).tryPostLogin(postRequest)
//            //서버 터질때는 그냥 이렇게 진행 -> 또 터졌다~~
//            val intent = Intent(activity, MainActivity::class.java)
//            startActivity(intent)
//            activity?.finish()
        }

        //두개가 글이 채워져있어야만 유효한것이므로 로그인 버튼이 활성화되게 진행함(색도 바뀜)
        loginCheck(binding.edtSignEmail)
        loginCheck(binding.edtSignPassword)


    }

    private fun postSignData(){

    }

    //다시 로그인 창으로 돌아감
    private fun backFragment(){
        parentFragmentManager.beginTransaction().remove(this@LoginSignFragment).commit()
    }


    //로그인 유효성 검사 (둘다 채워졌을때만 진행
    private fun loginCheck(editText: EditText) {
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) { checkLoginValidation() }
        })
    }

    private fun checkLoginValidation(): Boolean{
        val email = binding.edtSignEmail.text.toString()
        val password = binding.edtSignPassword.text.toString()
        return if(email.isNotEmpty() && password.isNotEmpty()){
            binding.btnSignLogin.setBackgroundResource(R.color.edittext_skyBlue)
            binding.btnSignLogin.isEnabled = true
            true
        }else{
            binding.btnSignLogin.setBackgroundResource(R.color.brighter_skyBlue)
            binding.btnSignLogin.isEnabled = false
            false
        }
    }

    override fun onPostJoinSuccess(response: PostJoinResponse) {}

    override fun onPostJoinFailure(message: String) {}

    @SuppressLint("CommitPrefEdits")
    override fun onPostLoginSuccess(response: PostLoginResponse) {
        val jwtEditor = sSharedPreferences.edit()
        dismissLoadingDialog()
        if(response.isSuccess){
            Log.d("test",response.code.toString())
            Log.d("test", response.postLoginResult.jwt.toString())
            Log.d("test",response.postLoginResult.userNum.toString())
            jwtEditor.putString(X_ACCESS_TOKEN,response.postLoginResult.jwt)
            jwtEditor.putInt("userNum", response.postLoginResult.userNum)
            jwtEditor.apply()
            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
    }

    override fun onPostLoginFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }
}