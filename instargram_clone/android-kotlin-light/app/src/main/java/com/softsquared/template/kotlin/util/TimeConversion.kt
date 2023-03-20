package com.softsquared.template.kotlin.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

class TimeConversion {
    companion object{
        @SuppressLint("SimpleDateFormat")
        private fun getTime(): String {
            val now = System.currentTimeMillis()
            val date = Date(now)

            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val getTime = dateFormat.format(date)

            return getTime
        }
        @SuppressLint("SimpleDateFormat")
        fun intervalBetweenDateText(beforeDate: String): String {
            //현재 시간
            val nowFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(getTime())
            val beforeFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(beforeDate)

            val diffSec     = (nowFormat.time - beforeFormat.time) / 1000             //몇 초 전 -> 방금전
            val diffMin     = (nowFormat.time - beforeFormat.time) / (60*1000)         //몇분 전
            val diffHor     = (nowFormat.time - beforeFormat.time) / (60 * 60 * 1000)  //몇시간 전
            val diffDays    = diffSec / (24 * 60 * 60)                                //몇일 전

            if (diffDays > 0){
                return "${diffDays}일 전"
            }
            else if(diffHor > 0){
                return "${diffHor}시간 전"
            }
            else if(diffMin > 0){
                return "${diffMin}분 전"
            }
            else{
                return "방금 전"
            }

        }
    }
}