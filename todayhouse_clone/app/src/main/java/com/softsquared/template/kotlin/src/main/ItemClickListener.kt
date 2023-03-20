package com.softsquared.template.kotlin.src.main

import android.view.View

interface ItemClickListener<T> {
    fun onItemClick(pos:Int, item:T)
}