package org.appeyroad.s13.giringrim

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

@SuppressLint("InflateParams")
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var lastSelected = navigation.selectedItemId
        navigation.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            val id = item.itemId
            if (id != R.id.navigation_settings) {
                lastSelected = id
            }
            return@OnNavigationItemSelectedListener when (id) {
                R.id.navigation_pen -> {
                    finger_canvas.tool = Tool.PEN
                    true
                }
                R.id.navigation_brush -> {
                    finger_canvas.tool = Tool.BRUSH
                    true
                }
                R.id.navigation_eraser -> {
                    finger_canvas.tool = Tool.ERASER
                    true
                }
                R.id.navigation_settings -> {
                    AlertDialog.Builder(this)
                            .setView(PaintDashboard(this))
                            .setOnDismissListener { navigation.selectedItemId = lastSelected }
                            .create()
                            .show()
                    true
                }
                else -> false
            }
        })
    }
}
