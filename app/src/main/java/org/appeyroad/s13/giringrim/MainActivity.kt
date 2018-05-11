package org.appeyroad.s13.giringrim

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            return@OnNavigationItemSelectedListener when (item.itemId) {
                R.id.navigation_pen -> {
                    finger_canvas.visibility = View.VISIBLE
                    finger_canvas.tool = Tool.PEN
                    true
                }
                R.id.navigation_brush -> {
                    finger_canvas.visibility = View.VISIBLE
                    finger_canvas.tool = Tool.BRUSH
                    true
                }
                R.id.navigation_eraser -> {
                    finger_canvas.visibility = View.VISIBLE
                    finger_canvas.tool = Tool.ERASER
                    true
                }
                R.id.navigation_settings -> {
                    finger_canvas.visibility = View.GONE
                    true
                }
                else -> false
            }
        })
    }
}
