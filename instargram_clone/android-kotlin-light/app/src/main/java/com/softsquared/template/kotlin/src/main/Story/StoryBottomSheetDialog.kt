package com.softsquared.template.kotlin.src.main.Story

import android.annotation.SuppressLint
import android.app.ActionBar.LayoutParams
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.softsquared.template.kotlin.databinding.DialogStoryBottomSheetBinding


class StoryBottomSheetDialog(context: Context) : Dialog(context){
    private lateinit var binding : DialogStoryBottomSheetBinding

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogStoryBottomSheetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomSheetDialog =

        window?.setLayout(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT)

        // Custom Dialog 위치 조절





    }
}