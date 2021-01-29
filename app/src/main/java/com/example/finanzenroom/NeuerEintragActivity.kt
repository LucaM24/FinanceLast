package com.example.finanzenroom

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import java.util.*

class NeuerEintragActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private lateinit var editWordView: EditText
    private lateinit var tV4: EditText
    private lateinit var bEinnahme : Button
    private lateinit var bAusgabe : Button
    private lateinit var spinner : Spinner
    private lateinit var pickDateBtn : Button

    @RequiresApi(Build.VERSION_CODES.N)
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_neuer_eintrag)
        editWordView = findViewById(R.id.edit_word)
        tV4 = findViewById(R.id.textView4)
        bEinnahme = findViewById(R.id.button_einnahme)
        bAusgabe = findViewById(R.id.button_ausgabe)
        spinner = findViewById(R.id.kategorien_spinner)
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        bEinnahme.setOnClickListener{
            bAusgabe.isEnabled = bAusgabe.isEnabled != true
            spinner.onItemSelectedListener = this
            ArrayAdapter.createFromResource(
                    this,
                    R.array.kategorienEinahmen,
                    android.R.layout.simple_spinner_item
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner.adapter = adapter
            }
        }

        bAusgabe.setOnClickListener{
            bEinnahme.isEnabled = bEinnahme.isEnabled != true
            spinner.onItemSelectedListener = this
            ArrayAdapter.createFromResource(
                    this,
                    R.array.kategorienAusgaben,
                    android.R.layout.simple_spinner_item
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner.adapter = adapter
            }
        }
        //Datepicker um Datumvvariable einzulesen
        pickDateBtn = findViewById(R.id.pickDateBtn)
        pickDateBtn.setOnClickListener{
            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                // Display Selected date in TextView
                tV4.setText(dayOfMonth + month + year)
            }, year, month, day)
            dpd.show()

        }



        val button = findViewById<Button>(R.id.button_save)
        button.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(editWordView.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val transBetrag = (editWordView.text).toString()
                val transArt = getArtDerAusgabe()
                val transKat = spinner.selectedItem.toString()
                //val transKat = tV3.text.toString()
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


    private fun getArtDerAusgabe(): String? { //Sorgt dafür dass alle der Eintrag von Ausgabe und Einnahme genormt ist über die beiden Strings
        if(bAusgabe.isEnabled && bEinnahme.isEnabled){
            Toast.makeText(this, "Bitte Art der Ausgabe wählen",Toast.LENGTH_LONG).show()
        }
        if(!bAusgabe.isEnabled && bEinnahme.isEnabled){
            return "Einnahme"
        }
        if(!bEinnahme.isEnabled && bAusgabe.isEnabled){
            return "Ausgabe"
        }
        return "Fehler"
    }

    companion object {
        const val EXTRA_REPLY = "com.example.android.wordlistsql.REPLY"
        const val betrag = "betrag"
        const val tag = "tag"
        const val kat = "kategorie"
        const val art = "art"
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (parent != null) {
            parent.getItemAtPosition(position)
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}
