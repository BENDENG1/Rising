package com.softsquared.template.kotlin.src.Login

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContentProviderCompat.requireContext
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.ApplicationClass.Companion.X_ACCESS_TOKEN
import com.softsquared.template.kotlin.config.ApplicationClass.Companion.sSharedPreferences
import com.softsquared.template.kotlin.config.BaseActivity
import com.softsquared.template.kotlin.databinding.ActivityJoinBinding
import com.softsquared.template.kotlin.src.Login.models.PostJoinEmailRequest
import com.softsquared.template.kotlin.src.Login.models.PostJoinPhoneRequest
import com.softsquared.template.kotlin.src.Login.models.PostJoinResponse
import com.softsquared.template.kotlin.src.Login.models.PostLoginResponse
import com.softsquared.template.kotlin.src.main.MainActivity
import com.softsquared.template.kotlin.util.ErrorDialog
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class JoinActivity : BaseActivity<ActivityJoinBinding>(ActivityJoinBinding::inflate),LoginPartInterface{
    //certificate는 따로 서버에서 구현을 못한다니 그냥 아무거나 입력하고 지나가는걸로 진행

    private var isEmail : Boolean = true //true -> email false -> phonenumber
    private var email : String =""
    private var birthDate  = ""
    private var phoneNumber = ""
    private var nickName : String =""
    private var password : String =""
    private val cal = Calendar.getInstance()
    private val year = cal.get(Calendar.YEAR)
    private val month = cal.get(Calendar.MONTH)
    private val day = cal.get(Calendar.DAY_OF_MONTH)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        joinStep()
        backToLogin()

    }


    //나중에 통신이 성공 -> 보여주기 이런식으로 진행
    @SuppressLint("UseCompatLoadingForDrawables")
    private fun joinStep(){

        var message : String = ""

        //회원가입이 전화번호인지 이메일인지 설정
        binding.btnJoinEmailPhoneNumber.setOnClickListener {
            binding.clJoinPhone.visibility = View.VISIBLE
            binding.clJoinEmail.visibility = View.GONE
            isEmail = false
        }
        binding.btnJoinPhoneEmail.setOnClickListener {
            binding.clJoinPhone.visibility = View.GONE
            binding.clJoinEmail.visibility = View.VISIBLE
            isEmail = true
        }

        //이메일에서 다음을 클릭했을때
        binding.btnJoinEmailNext.setOnClickListener {
            //나중에 유효성 검사해야함 Email

            email = binding.tiedtJoinEmailAddress.text.toString()
            val emailPattern : String = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
            if(email.isNotEmpty() && email.matches(emailPattern.toRegex())){
                binding.clJoinEmail.visibility = View.GONE
                binding.clJoinCertification.visibility = View.VISIBLE
            }else{
                binding.tiedtJoinEmailAddress.text?.clear()
                errorDialogStep("올바른 이메일 형식을 입력하세요.")
            }
        }

        //핸드폰에서 다음 클릭
        binding.btnJoinPhoneNext.setOnClickListener {
            phoneNumber = binding.tiedtJoinPhoneAddress.text.toString()
            val phoneRegex = "^01(?:0|1|[6-9])(?:\\d{7}|\\d{8})$".toRegex()
            if(phoneNumber.matches((phoneRegex))){
                binding.clJoinPhone.visibility =View.GONE
                binding.clJoinCertification.visibility = View.VISIBLE
            }else{
                errorDialogStep("올바른 전화번호 형식을 입력하세요.")
                binding.tiedtJoinPhoneAddress.text?.clear()
            }
        }

        //이거는 검증코드가 서버에서 안날라오니 패스
        binding.btnJoinCertificateNext.setOnClickListener {
            binding.clJoinCertification.visibility = View.GONE
            binding.clJoinName.visibility = View.VISIBLE
        }


        binding.btnJoinNameNext.setOnClickListener {
            val nickNameRegex = "^[A-Za-z0-9._-]+\$".toRegex()
            nickName = binding.tiedtJoinName.text.toString()
            if(nickName.matches(nickNameRegex)){
                binding.clJoinName.visibility = View.GONE
                binding.clJoinBirth.visibility = View.VISIBLE
            }else{
                errorDialogStep("올바른 닉네임 형식을 입력해 주세요.\n닉네임에는 영문자, 숫자, 마침표, 밑줄만 사용 가능합니다.")
                binding.tiedtJoinName.text?.clear()
            }
        }


        binding.tilJoinBirth.setOnClickListener { setBirthDate() }
        binding.tiedtJoinBirth.setOnClickListener{setBirthDate()}

        binding.btnJoinBirthNext.setOnClickListener {
            birthDate = binding.tiedtJoinBirth.text.toString()
            if(isValidBirthDate(birthDate)){
                binding.clJoinBirth.visibility  = View.GONE
                binding.clJoinPassword.visibility = View.VISIBLE
            }else{
                errorDialogStep("올바른 생년월일 형식을 확인하고 생년월일이 유효한지 확인해주세요.")
                binding.tiedtJoinBirth.text?.clear()
            }
        }


        //일단 여기서 가입을 시도하고 그다음에 설정에 대하여 컨트롤 하는걸로.
        binding.btnJoinPasswordNext.setOnClickListener {
            val passWordRegex = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}\$".toRegex()
            password = binding.tiedtJoinPassword.text.toString()
            if(password.matches(passWordRegex)){
                showLoadingDialog(this)
                if(isEmail) {
                    val postJoinEmailRequest = PostJoinEmailRequest(email,birthDate,nickName,password)
                    LoginService(this).tryPostEmailJoin(postJoinEmailRequest)
                }
                else{
                    val postJoinPhoneRequest = PostJoinPhoneRequest(phoneNumber,birthDate,nickName,password)
                    LoginService(this).tryPostPhoneJoin(postJoinPhoneRequest)
                }
            }else{
                errorDialogStep("올바른 비밀번호를 형식을 입력하세요.\n최소 6자이상의 숫자,문자가 최소 하나씩 이상이 들어가야합니다.")
                binding.tiedtJoinPassword.text?.clear()
            }
        }

        //자동로그인 설정
        binding.btnJoinStoreNext.setOnClickListener {storeLoginInformation(true)}
        binding.btnJoinStoreLater.setOnClickListener { storeLoginInformation(false) }


        binding.rgJoinPolicy.setOnCheckedChangeListener { radioGroup, rb ->
            val rb1 = binding.rbJoinPolicyFirstAgreement
            val rb2 = binding.rbJoinPolicySecondAgreement
            val rb3 = binding.rbJoinPolicyThirdAgreement

            if(rb1.isChecked && rb2.isChecked && rb3.isChecked){
                binding.clJoinPolicyChecked.setBackgroundResource(R.drawable.join_stroke_red_rectangle)
            }else{
                binding.clJoinPolicyChecked.setBackgroundResource(R.drawable.join_stroke_gray_rectangle)
            }
        }


        binding.btnJoinPolicyNext.setOnClickListener {
            val check1 = binding.rbJoinPolicyFirstAgreement.isChecked
            val check2 = binding.rbJoinPolicySecondAgreement.isChecked
            val check3 = binding.rbJoinPolicyThirdAgreement.isChecked
            if(check1 && check2 && check3){
                binding.clJoinPolicy.visibility = View.GONE
                binding.clJoinProfile.visibility = View.VISIBLE
            }else{
                errorDialogStep("필수 약관에 대해서 모두 동의해 주세요.")
                binding.clJoinPolicyChecked.background = getDrawable(R.drawable.join_stroke_red_rectangle)
            }
        }

        binding.btnJoinProfileNext.setOnClickListener { uploadProfile(true)}
        binding.btnJoinProfileSkip.setOnClickListener { uploadProfile(false) }




    }

    private fun backToLogin(){
        binding.tvJoinAlreadyAccount.setOnClickListener {
            finish()
        }
    }


    //여기는 이제 그때그때 오류를 체크 (빈값, 형식에 안맞는 -> 클라이언트 처리)
    private fun errorDialogStep(message : String){
        val sharedPref = sSharedPreferences.edit()
        sharedPref.putString("errorType","가입 오류")
        sharedPref.putString("errorMessage",message)
        sharedPref.apply()
        ErrorDialog(this).show()
    }

    //오류명에 대해서 이제 컨트롤 하는거임 (서버의 오류 ex)중복 -> 서버 데이터 처리)
    //코드가 나오게 된다면 그에 맞게 다시 예전으로 돌아가야 하기 때문에
    private fun errorDialogBack(code : Int){
        when(code){
            //클라이언트에서 다 걸렀기 때문에 이메일,닉네임 중복만 체크
            3002 ->{
                binding.clJoinPassword.visibility = View.GONE
                binding.clJoinNickname.visibility = View.VISIBLE
                errorDialogStep("중복된 이메일입니다.")
            }
            3004 ->{
                binding.clJoinPassword.visibility = View.GONE
                binding.clJoinEmail.visibility = View.VISIBLE
                errorDialogStep("중복된 닉네임입니다.")
            }
            4000 ->{
                errorDialogStep("데이터  베이스 연결에 실패하였습니다.")
            }
            4001 ->{
                errorDialogStep("서버와의 연결에 실패하였습니다.")
            }
        }
    }

    override fun onPostLoginSuccess(response: PostLoginResponse) {}

    override fun onPostLoginFailure(message: String) {}

    override fun onPostJoinSuccess(response: PostJoinResponse) {
        val sharedpref = sSharedPreferences.edit()
        dismissLoadingDialog()
        if(response.isSuccess){
            sharedpref.putString(X_ACCESS_TOKEN,response.postJoinResult.jwt)
            sharedpref.putInt("user_id",response.postJoinResult.user_id)
            sharedpref.putInt("my_id",response.postJoinResult.user_id)
            sharedpref.apply()
            binding.clJoinPassword.visibility = View.GONE
            binding.clJoinStore.visibility = View.VISIBLE
        }else{
            errorDialogBack(response.code)
        }
    }

    override fun onPostJoinFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("가입 실패 사유 : $message")
    }


    //생년월일 정규식 및 현재를 기준으로 이전이어야함
    private fun isValidBirthDate(birthDate: String): Boolean {
        val regex = Regex("^19[0-9]{2}|20[0-2]{1}[0-9]{1}-(0[1-9]{1}|1[0-2]{1})-([0-2]{1}[0-9]{1}|3[0-1]{1})$")
        if (!regex.matches(birthDate)) {
            return false
        }
        val currentYear = LocalDate.now().year
        val birthYear = birthDate.substring(0, 4).toInt()
        return birthYear < currentYear
    }

    private fun setBirthDate(){
        val datePickerDialog = DatePickerDialog( this, { _, year, month, day ->
            val birthDate = LocalDate.of(year, month + 1, day)
            val formattedBirthDate = birthDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
            binding.tiedtJoinBirth.setText(formattedBirthDate)
        }, year, month, day)
        datePickerDialog.show()
    }

    private fun storeLoginInformation(autoLogin : Boolean){
        if(autoLogin){
            sSharedPreferences.edit().putBoolean("autoLogin", true).apply()
        }else{
            sSharedPreferences.edit().putBoolean("autoLogin", false).apply()
        }

        binding.clJoinPolicy.visibility = View.VISIBLE
        binding.clJoinStore.visibility = View.GONE
    }

    private fun uploadProfile(profile : Boolean){

        val autoLogin = sSharedPreferences.getBoolean("autoLogin",false)
        if(autoLogin){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }else{
            finish()
        }
    }
}