package org.appeyroad.s13.giringrim

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import kotlin.math.abs

class FingerCanvas : View {

    companion object {

        private val bitmapPaint = Paint(Paint.DITHER_FLAG)
        private const val TOLERANCE = 4
    }

    private val prefs = Prefs(context)

    private var _tool = Tool.PEN
    var tool
        set(value) {
            _tool = value
        }
        get() = _tool.apply {
            paint.color = prefs.paintColor
            paint.strokeWidth = prefs.strokeWidth.toFloat()
        }

    private val prev = object { var x = 0f; var y = 0f }
    private val path = Path()

    private lateinit var bitmap: Bitmap
    private lateinit var canvas: Canvas

    constructor(context: Context): super(context)
    constructor(context: Context, attrs: AttributeSet): super(context, attrs)

    init {
        setLayerType(View.LAYER_TYPE_SOFTWARE, null)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        canvas = Canvas(bitmap)
    }

    override fun onDraw(canvas: Canvas) {
        canvas.run {
            drawColor(Color.WHITE)
            drawBitmap(bitmap, 0f, 0f, bitmapPaint)
            drawPath(path, tool.paint)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y

        when (event.action) {
            MotionEvent.ACTION_DOWN -> onTouchDown(x, y)
            MotionEvent.ACTION_MOVE -> onTouchMove(x, y)
            MotionEvent.ACTION_UP -> onTouchUp(x, y)
            else -> return true
        }
        invalidate()

        return true
    }

    private fun onTouchDown(x: Float, y: Float) {
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
        tool.draw(path, prev.x, prev.y, x, y)
        prev.x = x
        prev.y = y
    }

    private fun onTouchUp(x: Float, y: Float) {
        path.lineTo(x, y)
        canvas.drawPath(path, tool.paint)
        path.reset()
    }
}
