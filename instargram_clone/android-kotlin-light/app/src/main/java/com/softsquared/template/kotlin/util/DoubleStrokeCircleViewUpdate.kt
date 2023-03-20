package com.softsquared.template.kotlin.util

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import com.softsquared.template.kotlin.R

@SuppressLint("ResourceAsColor")
class DoubleStrokeCircleViewUpdate(context: Context, attrs: AttributeSet?) :
    AppCompatImageView(context, attrs) {

    private val paintBorder = Paint()
    private val paintBorder2 = Paint()

    init {
        paintBorder.style = Paint.Style.STROKE
        paintBorder.color = R.color.btn_story_red
        paintBorder.strokeWidth = 10f
        paintBorder.isAntiAlias = true

        paintBorder2.style = Paint.Style.STROKE
        paintBorder2.color = R.color.btn_story_gray
        paintBorder2.strokeWidth = 10f
        paintBorder2.isAntiAlias = true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawCircle(
            (width / 2).toFloat(),
            (height / 2).toFloat(),
            (width / 2 - 10).toFloat(),
            paintBorder
        )
        canvas.drawCircle(
            (width / 2).toFloat(),
            (height / 2).toFloat(),
            (width / 2 - 15).toFloat(),
            paintBorder2
        )
    }
}





