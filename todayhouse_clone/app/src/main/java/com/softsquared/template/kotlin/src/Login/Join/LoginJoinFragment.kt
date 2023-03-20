package com.softsquared.template.kotlin.src.Login.Join

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.Toast
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.BaseFragment
import com.softsquared.template.kotlin.databinding.FragmentLoginJoinBinding
import com.softsquared.template.kotlin.src.Login.LoginFragmentInterface
import com.softsquared.template.kotlin.src.Login.LoginService
import com.softsquared.template.kotlin.src.Login.models.PostJoinRequest
import com.softsquared.template.kotlin.src.Login.models.PostJoinResponse
import com.softsquared.template.kotlin.src.Login.models.PostLoginResponse
import com.softsquared.template.kotlin.src.main.MainActivity
import java.util.regex.Pattern

class LoginJoinFragment : BaseFragment<FragmentLoginJoinBinding> (FragmentLoginJoinBinding::bind, R.layout.fragment_login_join)
    ,LoginFragmentInterface{

    private val emailValidation by lazy { "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$" }
    private val passwordValidation by lazy { "^[A-Za-z0-9]{8,20}"}
    var allValidation = BooleanArray(5) { false } //아이디, 패스워드,패스워드, 닉네임, 체크리스트

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //다시 전 프래그먼트로 돌아감
        binding.ibtnLoginJoinBack.setOnClickListener {
            backFragment()
        }
        //가입하게 되면서 로그인창으로 가게됨
        binding.btnJoinComplete.setOnClickListener {
            val email = binding.edtJoinEmail.text.toString()
            val password = binding.edtJoinPassword.text.toString()
            val nickname = binding.edtJoinNickname.text.toString()
            val check4 =  booleanToInt(binding.checkBoxJoinAgree4.isChecked)
            val check5 = booleanToInt(binding.checkBoxJoinAgree5.isChecked)
            val joinRequest = PostJoinRequest(email,password,nickname,check4,check5)
            LoginService(this).tryPostJoin(joinRequest)
        }

        validationChangeCheck() //editText들 유효성검사
        checkBoxCheckChangeListener() //checkbox들 유효성 검사


    }

    fun booleanToInt(b:Boolean):Int{
        return b.compareTo(false)
    }



    private fun allValidationPass(){
        if(allValidation.all { it }){
            //다 되었을때
            binding.btnJoinComplete.isEnabled = true
            binding.btnJoinComplete.setBackgroundResource(R.color.edittext_skyBlue)
        }else{
            binding.btnJoinComplete.isEnabled = false
            binding.btnJoinComplete.setBackgroundResource((R.color.brighter_skyBlue))
            return
        }
    }

    private fun checkBoxCheckChangeListener(){
        with(binding){
            checkBoxJoinAgreeAll.setOnClickListener{checkBoxonCheckChanged(checkBoxJoinAgreeAll)}
            checkBoxJoinAgree1.setOnClickListener{checkBoxonCheckChanged(checkBoxJoinAgree1)}
            checkBoxJoinAgree2.setOnClickListener{checkBoxonCheckChanged(checkBoxJoinAgree2)}
            checkBoxJoinAgree3.setOnClickListener{checkBoxonCheckChanged(checkBoxJoinAgree3)}
            checkBoxJoinAgree4.setOnClickListener{checkBoxonCheckChanged(checkBoxJoinAgree4)}
            checkBoxJoinAgree5.setOnClickListener{checkBoxonCheckChanged(checkBoxJoinAgree5)}
        }
    }
    private fun checkBoxonCheckChanged(compoundButton: CompoundButton){
        val checkBoxArray = arrayOf(binding.checkBoxJoinAgree1, binding.checkBoxJoinAgree2
            ,binding.checkBoxJoinAgree3,binding.checkBoxJoinAgree4,binding.checkBoxJoinAgree5)
        when(compoundButton.id){
            R.id.checkBox_join_agreeAll ->{
                if(binding.checkBoxJoinAgreeAll.isChecked){
                    for(i in 0 until 5){
                        checkBoxArray[i].isChecked = true
                    }
                    binding.tvJoinCheckBoxWrong.visibility = View.GONE
                    binding.clJoinAgree.setBackgroundResource(R.drawable.login_join_layout_layer_true)
                    allValidation[4] = true
                    allValidationPass()
                }
                else{
                    for(i in 0 until 5){ checkBoxArray[i].isChecked = false }
                    binding.tvJoinCheckBoxWrong.visibility = View.VISIBLE
                    binding.clJoinAgree.setBackgroundResource(R.drawable.login_join_layout_layer)
                    allValidationPass()
                }
            }
            else ->{
                with(binding){
                    checkBoxJoinAgreeAll.isChecked = (checkBoxArray.all { it.isChecked })
                    if(checkBoxJoinAgree1.isChecked && checkBoxJoinAgree2.isChecked &&checkBoxJoinAgree3.isChecked) {
                        tvJoinCheckBoxWrong.visibility = View.GONE
                        clJoinAgree.setBackgroundResource(R.drawable.login_join_layout_layer_true)
                        allValidation[4] = true
                        allValidationPass()
                    }else{
                        tvJoinCheckBoxWrong.visibility = View.VISIBLE
                        clJoinAgree.setBackgroundResource(R.drawable.login_join_layout_layer)
                        allValidationPass()
                    }
                }
            }
        }
    }


    private fun backFragment(){
        parentFragmentManager.beginTransaction().remove(this@LoginJoinFragment).commit()
    }

    private fun validationChangeCheck(){
        binding.edtJoinEmail.addTextChangedListener(object : TextWatcher{
            //text가 변경된 후 에 호출 , s에는 변경후의 문자열이 담겨있음
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            //text가 변경되기전에 호출 , s에는 변경 전 문자열이 담겨있음
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            //text가 바뀔 때마다 호출된다.
            override fun afterTextChanged(p0: Editable?) { checkEmail() }
        })
        binding.edtJoinPassword.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) { checkPassword() }
        })
        binding.edtJoinPasswordTwice.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) { checkPasswordTwice() }
        })
        binding.edtJoinNickname.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) { checkNickname() }
        })
        binding.edtJoinRecommendNum.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) { checkRecommendNum() }
        })

    }

    private fun checkEmail():Boolean{
        val email = binding.edtJoinEmail.text.toString().trim() //공백제거
        val pattern = Pattern.matches(emailValidation, email) // 패턴 체크
        return if (pattern) { //이메일 형태가 정상일 경우
            binding.clJoinEmail.setBackgroundResource(R.drawable.login_join_layout_layer_true)
            binding.tvJoinEmailCorrect.visibility = View.GONE
            allValidation[0] = true
            allValidationPass()
            true
        } else {
            binding.clJoinEmail.setBackgroundResource(R.drawable.login_join_layout_layer)
            binding.tvJoinEmailCorrect.visibility = View.VISIBLE
            allValidation[0] = false
            allValidationPass()
            false
        }
    }

    private fun checkPassword():Boolean{
        val password = binding.edtJoinPassword.text.toString()
        val pattern = Pattern.matches(passwordValidation,password)
        return if(pattern){
            binding.clJoinPassword.setBackgroundResource(R.drawable.login_join_layout_layer_true)
            binding.tvJoinPasswordWrong.visibility = View.GONE
            allValidation[1] = true
            allValidationPass()
            true
        }else{
            binding.clJoinPassword.setBackgroundResource(R.drawable.login_join_layout_layer)
            binding.tvJoinPasswordWrong.visibility = View.VISIBLE
            allValidation[1] = false
            allValidationPass()
            false
        }
    }

    private fun checkPasswordTwice():Boolean{
        val password = binding.edtJoinPassword.text.toString()
        val passwordTwice = binding.edtJoinPasswordTwice.text.toString()
        return if (password != passwordTwice) {
            binding.clJoinPasswordTwice.setBackgroundResource(R.drawable.login_join_layout_layer)
            binding.tvJoinPasswordTwiceWrong.visibility = View.VISIBLE
            allValidation[2] = false
            allValidationPass()
            false
        } else {
            binding.clJoinPasswordTwice.setBackgroundResource(R.drawable.login_join_layout_layer_true)
            binding.tvJoinPasswordTwiceWrong.visibility = View.GONE
            allValidation[2] = true
            allValidationPass()
            true
        }
    }

    private fun checkNickname():Boolean {
        val nickname = binding.edtJoinNickname.text.toString()
        return if (nickname.length in 2 .. 15){
            binding.clJoinNickname.setBackgroundResource(R.drawable.login_join_layout_layer_true)
            binding.tvJoinNicknameWrong.visibility = View.GONE
            allValidation[3] = true
            allValidationPass()
            true
        }else{
            binding.clJoinNickname.setBackgroundResource(R.drawable.login_join_layout_layer)
            binding.tvJoinNicknameWrong.visibility = View.VISIBLE
            allValidation[3] = false
            allValidationPass()
            false
        }
    }
    private fun checkRecommendNum(): Boolean{
        val recommend = binding.edtJoinRecommendNum.text.toString()
        return if(recommend.length > 1){
            binding.btnJoinRecommendCheck.setBackgroundResource(R.color.edittext_skyBlue)
            binding.btnJoinRecommendCheck.isEnabled = true
            allValidationPass()
            true
        }else{
            binding.btnJoinRecommendCheck.setBackgroundResource(R.color.brighter_skyBlue)
            binding.btnJoinRecommendCheck.isEnabled = false
            allValidationPass()
            false
        }
    }

    override fun onPostJoinSuccess(response: PostJoinResponse) {
        Log.d("----","${response.code}")
        showCustomToast(response.result)
        backFragment()
    }

    override fun onPostJoinFailure(message: String) {
        showCustomToast("오류 : $message")
    }

    override fun onPostLoginSuccess(response: PostLoginResponse) {}

    override fun onPostLoginFailure(message: String) {}
}