package com.softsquared.template.kotlin.util

interface ItemClickListener<T>{
    fun onItemClick(pos:Int, item:T)
}