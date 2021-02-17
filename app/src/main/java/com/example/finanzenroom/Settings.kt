package com.example.finanzenroom

import android.os.Bundle
import android.view.View
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import android.app.UiModeManager as UiModeManager1

class Settings: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_screen)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) //Dadurch wird in der Actionbar ein Pfeil angezeigt, mit dem man zum Hauptbildschirm, (oder auf ein beliebiges), wechseln kann

        var settings = findViewById<TextView>(R.id.textView_settings)
        settings.text = "Einstellungen"

        var tvDarkMode = findViewById<TextView>(R.id.textView_darkmode)
        tvDarkMode.text = "Dark Mode"
        tvDarkMode.setOnClickListener {

        }

        var tvRateUs = findViewById<TextView>(R.id.textView_rateus)
        tvRateUs.text = "Rate us!"
        var rateUs = findViewById<RatingBar>(R.id.ratingBar)

    }
}