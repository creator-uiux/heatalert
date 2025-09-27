package com.example.heatalert.Settings

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.heatalert.R

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_settings)
        val tvAdvancedSettings = findViewById<TextView>(R.id.tv_advanced_settings)
        val layoutAdvancedControls = findViewById<LinearLayout>(R.id.layout_advanced_controls)

        tvAdvancedSettings.setOnClickListener {
            if (layoutAdvancedControls.visibility == View.GONE) {
                layoutAdvancedControls.visibility = View.VISIBLE
                tvAdvancedSettings.text = "Hide Advanced Settings"
            } else {
                layoutAdvancedControls.visibility = View.GONE
                tvAdvancedSettings.text = "Advanced Settings"
            }
        }
    }
}