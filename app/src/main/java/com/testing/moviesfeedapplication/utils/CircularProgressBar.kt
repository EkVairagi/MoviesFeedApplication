package com.testing.moviesfeedapplication.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class CircularProgressBar(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private var progress: Int = 0
    private val paint: Paint = Paint().apply {
        isAntiAlias = true
        strokeWidth = 20f
        style = Paint.Style.STROKE
        color = Color.parseColor("#D2D531")
    }

    private val textPaint: Paint = Paint().apply {
        isAntiAlias = true
        color = Color.BLACK
        textSize = 30f
        textAlign = Paint.Align.CENTER
    }

    fun setProgress(progress: Int) {
        this.progress = progress
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val width = width
        val height = height
        val radius = width.coerceAtMost(height) / 2.0f - paint.strokeWidth

        paint.color = Color.parseColor("#423D0F")
        canvas.drawCircle(width / 2.0f, height / 2.0f, radius, paint)

        paint.color =  Color.parseColor("#D2D531")
        val sweepAngle = 360 * progress / 100.0f
        canvas.drawArc(
            paint.strokeWidth,
            paint.strokeWidth,
            width - paint.strokeWidth,
            height - paint.strokeWidth,
            -90f,
            sweepAngle,
            false,
            paint
        )

        val text = "$progress%"
        val xPos = width / 2.0f
        val yPos = (height / 2.0f - (textPaint.descent() + textPaint.ascent()) / 2)
        canvas.drawText(text, xPos, yPos, textPaint)
    }
}
