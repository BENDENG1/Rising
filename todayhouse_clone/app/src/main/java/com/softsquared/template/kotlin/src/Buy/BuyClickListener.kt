package com.softsquared.template.kotlin.src.Buy

import com.softsquared.template.kotlin.databinding.BuyCartListBinding

interface BuyClickListener<T> {
    fun onItemClick(pos:Int, item:T)
    fun onViewClick(view : BuyCartListBinding,pos: Int)
}