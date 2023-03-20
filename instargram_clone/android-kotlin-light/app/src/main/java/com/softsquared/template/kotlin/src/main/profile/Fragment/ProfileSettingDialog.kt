package com.softsquared.template.kotlin.src.main.profile.Fragment

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import com.softsquared.template.kotlin.databinding.DialogProfileSettingBinding

class ProfileSettingDialog(context: Context) : Dialog(context) {
    private lateinit var binding : DialogProfileSettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogProfileSettingBinding.inflate(layoutInflater)

        val settingDialog = Dialog(context)
        settingDialog.setContentView(binding.root)

        settingDialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)

        settingDialog.window?.setGravity(Gravity.BOTTOM)
        settingDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        settingDialog.show()
    }



}