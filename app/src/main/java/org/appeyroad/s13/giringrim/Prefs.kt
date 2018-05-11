package org.appeyroad.s13.giringrim

import android.content.Context
import android.graphics.Color

class Prefs(context: Context) {

    private companion object {

        const val PAINT_COLOR_KEY = "color"
        const val PAINT_COLOR_DEFAULT = Color.BLACK
        const val STROKE_WIDTH_KEY = "stroke_width"
        const val STROKE_WIDTH_DEFAULT = 10
    }

    private val sp = context.getSharedPreferences(null, Context.MODE_PRIVATE)

    var paintColor
        set(value) {
            sp.edit().apply {
                putInt(PAINT_COLOR_KEY, value)
                apply()
            }
        }
        get() = sp.getInt(PAINT_COLOR_KEY, PAINT_COLOR_DEFAULT)

    var strokeWidth
        set(value) {
            sp.edit().apply{
                putInt(STROKE_WIDTH_KEY, value)
                apply()
            }
        }
        get() = sp.getInt(STROKE_WIDTH_KEY, STROKE_WIDTH_DEFAULT)
}
