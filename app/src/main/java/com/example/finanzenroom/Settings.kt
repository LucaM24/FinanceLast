package com.example.finanzenroom

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import android.app.UiModeManager as UiModeManager1

class Settings: AppCompatActivity() {
    @SuppressLint("ShowToast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_screen)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) //Dadurch wird in der Actionbar ein Pfeil angezeigt, mit dem man zum Hauptbildschirm, (oder auf ein beliebiges), wechseln kann

        //Überschrift
        var settings = findViewById<TextView>(R.id.textView_settings)
        settings.text = "Einstellungen"

        var tvRateUs = findViewById<TextView>(R.id.textView_rateus)
        tvRateUs.text = "Rate us!"
        var rateUs = findViewById<RatingBar>(R.id.ratingBar)
        rateUs.setOnClickListener(View.OnClickListener {
            Toast.makeText(this, "Danke für dein Rating!",Toast.LENGTH_LONG)
        })

        var tvDarkMode = findViewById<TextView>(R.id.textView_darkmode)
        tvDarkMode.text = "Dark Mode"

        var creditsTextView = findViewById<TextView>(R.id.credits_tv)
        creditsTextView.text = "App designed von\n" + "Feyzi Gencer\n" +"Luca Michalczyk\n"+"Fatih Dülgerog"

        //Versuche ich noch zu implementieren, wenns nicht klappt einfach entfernen
        var darkMode = findViewById<Button>(R.id.switchToDarkMode)
        darkMode.setOnClickListener{

        }
    }
}