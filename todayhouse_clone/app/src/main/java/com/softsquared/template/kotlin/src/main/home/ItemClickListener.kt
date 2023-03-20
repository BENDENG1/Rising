package com.softsquared.template.kotlin.src.main.home

interface ItemClickListener<T> {
    fun onItemClick(pos:Int, item:T)
}