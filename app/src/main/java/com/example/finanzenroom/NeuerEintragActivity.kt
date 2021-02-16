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
import java.time.DayOfWeek
import java.util.*

class NeuerEintragActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private lateinit var editWordView: EditText
    private lateinit var tV4: EditText
    private lateinit var bEinnahme : Button
    private lateinit var bAusgabe : Button
    private lateinit var spinner : Spinner
    private lateinit var pickDateBtn : Button
    private lateinit var neuerEintragÜberschrift: TextView

    @RequiresApi(Build.VERSION_CODES.N)
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_neuer_eintrag)
        editWordView = findViewById(R.id.edit_word)
        tV4 = findViewById(R.id.textView4)
        bEinnahme = findViewById(R.id.button_einnahme)
        bAusgabe = findViewById(R.id.button_ausgabe)
        spinner = findViewById(R.id.kategorien_spinner)

        neuerEintragÜberschrift = findViewById(R.id.neuereintragüberschrift)
        neuerEintragÜberschrift.text = "Neuer Eintrag"

        bEinnahme.setOnClickListener{
            bAusgabe.isEnabled = true
            bEinnahme.isEnabled = false
            bEinnahme.isClickable = true
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
            bEinnahme.isEnabled = true
            bAusgabe.isEnabled = false
            bAusgabe.isClickable = true
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
        var c = Calendar.getInstance()
        var year = c.get(Calendar.YEAR)
        var month = c.get(Calendar.MONTH) + 1
        var day = c.get(Calendar.DAY_OF_MONTH)
        tV4.setText((day.toString()+ "." + month.toString() + "." + year.toString()))
        fun date() { //3. Methode, falls der Nutzer einen anderen Tag auswählen möchte
            val datePickerDialog =
                    DatePickerDialog(this@NeuerEintragActivity, DatePickerDialog.OnDateSetListener
                    { view, yearOfYear, monthOfYear, dayOfMonth ->
                        tV4.setText((dayOfMonth.toString()+ "." + (monthOfYear + 1).toString() + "." + yearOfYear.toString())) //3.1 Datum im Button wird mit dem ausgewählten ersetzt
                        day = dayOfMonth //3.2 Die Variablen Tag, Monat und Jahr, welche vorher das Livedatum waren, werden auf die vom Nutzer ausgewählten Daten gesetzt
                        month = monthOfYear + 1
                        year = yearOfYear
                        //scrollview.text = ""+day+"."+month+"."+year //Nur zum Test (kann gelöscht werden)
                    }, year, month, day
                    )
            datePickerDialog.show()
        }
        pickDateBtn = findViewById(R.id.pickDateBtn)
        pickDateBtn.setOnClickListener(){
            date()
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
                //val taggo = day.toString()+month.toString()+year.toString()
                //val transTag = taggo
                replyIntent.putExtra(betrag, transBetrag)
                replyIntent.putExtra(jahr, year)
                replyIntent.putExtra(monat, month)
                replyIntent.putExtra(tag, day)
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
        const val kat = "kategorie"
        const val art = "art"
        const val jahr = "jahr"
        const val monat = "monat"
        const val tag = "tag"
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
