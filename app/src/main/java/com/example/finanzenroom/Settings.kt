package com.example.finanzenroom

import android.os.Bundle
import android.widget.RatingBar
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class Settings: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_screen)

        var settings = findViewById<TextView>(R.id.textView_settings)
        settings.text = "Settings"

        var tvDarkMode = findViewById<TextView>(R.id.textView_darkmode)
        tvDarkMode.text = "Dark Mode"

        var darkMode = findViewById<Switch>(R.id.switchToDarkMode)
        darkMode.setOnClickListener {

        }

        var tvRateUs = findViewById<TextView>(R.id.textView_rateus)
        tvRateUs.text = "Rate us!"

        var rateUs = findViewById<RatingBar>(R.id.ratingBar)
    }
}