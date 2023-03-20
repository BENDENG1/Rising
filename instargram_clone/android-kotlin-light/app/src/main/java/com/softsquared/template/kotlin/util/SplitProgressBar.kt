package com.softsquared.template.kotlin.util

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import java.lang.Integer.min

class SplitProgressBar(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    private val progressPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val backgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var progress = 0
    private var totalBars = 1

    init {
        progressPaint.color = Color.WHITE
        backgroundPaint.color = Color.GRAY
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val barWidth = width / totalBars
        var drawProgress = progress
        for (i in 0 until totalBars) {
            val left = barWidth * i
            val right = left + barWidth
            val barProgress = min(drawProgress, 1)
            drawProgress -= 1
            canvas?.drawRect(left.toFloat(), 0f, right.toFloat() * barProgress, height.toFloat(), progressPaint)
            if (barProgress < 1) {
                canvas?.drawRect(right.toFloat() * barProgress, 0f, right.toFloat(), height.toFloat(), backgroundPaint)
            }
        }
    }

    fun setProgress(progress: Int, totalBars: Int) {
        this.progress = progress
        this.totalBars = totalBars
        invalidate()
    }
}