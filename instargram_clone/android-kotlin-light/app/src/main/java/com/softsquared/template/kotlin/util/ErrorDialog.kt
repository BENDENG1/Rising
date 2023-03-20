package com.softsquared.template.kotlin.util

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.softsquared.template.kotlin.config.ApplicationClass.Companion.sSharedPreferences
import com.softsquared.template.kotlin.databinding.ErrorDialogBinding

class ErrorDialog(context: Context) : Dialog(context) {
    private lateinit var binding : ErrorDialogBinding
//    private var sSharedPreferences : SharedPreferences = context.getSharedPreferences("message",Context.MODE_PRIVATE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ErrorDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setCancelable(false) // false -> 화면 밖 터치 막음


        val message = sSharedPreferences.getString("errorMessage",null)
        val errorMessage = sSharedPreferences.getString("errorType",null)

        binding.tvLoginErrorTitle.text = errorMessage
        binding.tvLoginErrorMessage.text = message

        binding.tvLoginErrorAgain.setOnClickListener {
            dismiss()
        }

    }
}