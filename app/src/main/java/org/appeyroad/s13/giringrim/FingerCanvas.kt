package org.appeyroad.s13.giringrim

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import kotlin.math.abs

class FingerCanvas(context: Context) : View(context) {

    companion object {

        private val bitmapPaint = Paint(Paint.DITHER_FLAG)
        private val TOLERANCE = 4
    }

    constructor(context: Context, attrs: AttributeSet): this(context)

    private val prev = object { var x = 0f; var y = 0f }
    private val path = Path()
    private val paint = Paint().apply {
        isAntiAlias = true
        isDither = true
        color = Color.BLACK
        style = Paint.Style.STROKE
        strokeJoin = Paint.Join.ROUND
        strokeCap = Paint.Cap.ROUND
        strokeWidth = 12f
    }

    private lateinit var bitmap: Bitmap
    private lateinit var canvas: Canvas

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        canvas = Canvas(bitmap)
    }

    override fun onDraw(canvas: Canvas) {
        canvas.run {
            drawColor(Color.WHITE)
            drawBitmap(bitmap, 0f, 0f, bitmapPaint)
            drawPath(path, paint)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y

        when (event.action) {
            MotionEvent.ACTION_DOWN -> onTouchStart(x, y)
            MotionEvent.ACTION_MOVE -> onTouchMove(x, y)
            MotionEvent.ACTION_UP -> onTouchDown(x, y)
            else -> return true
        }
        invalidate()

        return true
    }

    private fun onTouchStart(x: Float, y: Float) {
        path.reset()
        path.moveTo(x, y)
        prev.x = x
        prev.y = y
    }

    private fun onTouchMove(x: Float, y: Float) {
        val dx = abs(x - prev.x)
        val dy = abs(y - prev.y)
        if (dx < TOLERANCE || dy < TOLERANCE) {
            return
        }
        path.quadTo(x, y, (x + prev.x) / 2, (y + prev.y) / 2)
        prev.x = x
        prev.y = y
    }

    private fun onTouchDown(x: Float, y: Float) {
        path.lineTo(x, y)
        canvas.drawPath(path, paint)
        path.reset()
    }
}
