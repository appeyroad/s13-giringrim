package org.appeyroad.s13.giringrim

import android.graphics.*

enum class Tool {

    PEN {

        override val paint = Paint().apply {
            isAntiAlias = true
            isDither = true
            color = DEFAULT_COLOR
            style = Paint.Style.STROKE
            strokeJoin = Paint.Join.ROUND
            strokeCap = Paint.Cap.ROUND
            strokeWidth = DEFAULT_WIDTH
        }

        override fun draw(path: Path, prevX: Float, prevY: Float, x: Float, y: Float) {
            path.quadTo(prevX, prevY, (prevX + x) * 0.5f, (prevY + y) * 0.5f)
        }
    },

    BRUSH {

        private val offset = 3
        private val step
            get() = paint.strokeWidth / offset

        override val paint = Paint().apply {
            isAntiAlias = true
            isDither = true
            color = DEFAULT_COLOR
            style = Paint.Style.STROKE
            strokeJoin = Paint.Join.ROUND
            strokeCap = Paint.Cap.ROUND
            strokeWidth = DEFAULT_WIDTH * 0.5f
        }

        override fun draw(path: Path, prevX: Float, prevY: Float, x: Float, y: Float) {
            for (i in (-offset..offset)) {
                val d = i * step
                path.moveTo(prevX + d, prevY + d)
                path.lineTo(x + d, y + d)
            }
        }
    },

    ERASER {

        override val paint = Paint(PEN.paint).apply {
            xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
        }

        override fun draw(path: Path, prevX: Float, prevY: Float, x: Float, y: Float) {
            path.quadTo(prevX, prevY, (prevX + x) * 0.5f, (prevY + y) * 0.5f)
        }
    }
    ;

    private companion object {

        const val DEFAULT_WIDTH = 16f
        const val DEFAULT_COLOR = Color.BLACK
    }

    abstract val paint: Paint

    abstract fun draw(path: Path, prevX: Float, prevY: Float, x: Float, y: Float)
}