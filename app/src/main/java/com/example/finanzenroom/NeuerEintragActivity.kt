package com.example.finanzenroom

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class NeuerEintragActivity : AppCompatActivity() {
    private lateinit var editWordView: EditText
    private lateinit var tV2: EditText
    private lateinit var tV3: EditText
    private lateinit var tV4: EditText

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_neuer_eintrag)
        editWordView = findViewById(R.id.edit_word)
        tV2 = findViewById(R.id.textView2)
        tV3 = findViewById(R.id.textView3)
        tV4 = findViewById(R.id.textView4)


        val button = findViewById<Button>(R.id.button_save)
        button.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(editWordView.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val transBetrag = (editWordView.text).toString()
                val transArt = tV2.text.toString()
                val transKat = tV3.text.toString()
                val transTag = tV4.text.toString()
                replyIntent.putExtra(betrag, transBetrag)
                replyIntent.putExtra(tag, transTag)
                replyIntent.putExtra(kat, transKat)
                replyIntent.putExtra(art, transArt)
                replyIntent.putExtra(EXTRA_REPLY, "hallo")
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
    }

    companion object {
        const val EXTRA_REPLY = "com.example.android.wordlistsql.REPLY"
        const val betrag = "betrag"
        const val tag = "tag"
        const val kat = "kategorie"
        const val art = "art"
    }
}
