package com.softsquared.template.kotlin.src

interface ItemClickListener<T> {
    fun onItemClick(pos:Int, item:T)
}