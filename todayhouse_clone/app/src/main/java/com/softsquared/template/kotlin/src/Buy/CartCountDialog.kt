package com.softsquared.template.kotlin.src.Buy

import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.databinding.DialogCartCountBinding

class CartCountDialog(context: Context) : Dialog(context){

    private lateinit var binding : DialogCartCountBinding
    private lateinit var countsharedpref : SharedPreferences // context.getSharedPreferences("countNum",Context.MODE_PRIVATE)
    val editor : SharedPreferences.Editor = countsharedpref.edit()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)





    }

    private fun destroyDialog(){
        binding.btnCartCountPoint1.setOnClickListener {
            editor.putInt("countNum",1)
            dismiss()
        }
    }


}