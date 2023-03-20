package com.softsquared.template.kotlin.src.main.home

import com.softsquared.template.kotlin.databinding.HomeTipslistBinding

interface HomeTipClickListener<T> {
    fun onItemClick(pos:Int, item:T)
    fun onViewClick(view : HomeTipslistBinding,pos:Int)
}