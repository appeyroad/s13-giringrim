package org.appeyroad.s13.giringrim

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.SeekBar
import kotlinx.android.synthetic.main.paint_dashboard.view.*

public class PaintDashboard(context: Context): RelativeLayout(context) {

    private val prefs = Prefs(context)

    private var previewColor
        set(value) {
            prefs.paintColor = value
            onPreviewColorChange(value)
        }
        get() = prefs.paintColor
    var colorR
        set(value) {
            previewColor = Color.rgb(value, colorG, colorB)
        }
        get() = Color.red(prefs.paintColor)
    var colorG
        set(value) {
            previewColor = Color.rgb(colorR, value, colorB)
        }
        get() = Color.green(prefs.paintColor)
    var colorB
        set(value) {
            previewColor = Color.rgb(colorR, colorG, value)
        }
        get() = Color.blue(prefs.paintColor)

    var strokeWidth
        set(value) {
            onStrokeWidthChange(value)
            prefs.strokeWidth = value
        }
        get() = prefs.strokeWidth

    private val l = object: SeekBar.OnSeekBarChangeListener {

        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            when (seekBar?.id) {
                color_r.id -> colorR = progress
                color_g.id -> colorG = progress
                color_b.id -> colorB = progress
                stroke_width.id -> strokeWidth = progress
            }
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {}
        override fun onStopTrackingTouch(seekBar: SeekBar?) {}
    }

    init {
        View.inflate(context, R.layout.paint_dashboard, this)
        color_r.progress = colorR
        color_g.progress = colorG
        color_b.progress = colorB
        stroke_width.progress = strokeWidth
        onPreviewColorChange(previewColor)
        onStrokeWidthChange(strokeWidth)
        color_r.setOnSeekBarChangeListener(l)
        color_g.setOnSeekBarChangeListener(l)
        color_b.setOnSeekBarChangeListener(l)
        stroke_width.setOnSeekBarChangeListener(l)
    }

    private fun onStrokeWidthChange(value: Int) {
        preview.layoutParams = preview.layoutParams.apply {
            height = value
        }
    }

    private fun onPreviewColorChange(value: Int) {
        preview.setImageDrawable(ColorDrawable(value))
    }
}
