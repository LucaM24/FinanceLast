package com.example.finanzenroom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class Stats : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    //Elemente um Saldo des Monats zu berechnen
    private lateinit var spinner_monate : Spinner
    private lateinit var button_berechne : Button
    private lateinit var tV2 : TextView
    private lateinit var tV3 : TextView
    private lateinit var tV4 : TextView
    private lateinit var tVInt : TextView
    //Elemente um Summe von Kategorien eines Monats zu bestimmen
    private lateinit var tV7 : TextView
    private lateinit var spinner_kat : Spinner
    private lateinit var button_berechne2: Button


    val monate = arrayOf("Januar", "Februar", "März", "April", "Mai", "Juni", "Juli", "August", "September", "Oktober", "November", "Dezember")
    //
    private val transactionViewModel: TransactionViewModel by viewModels {
        WordViewModelFactory((application as TransactionApplication).repository)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stats)

        spinner_monate = findViewById(R.id.spinner_monate)
        spinner_monate.onItemSelectedListener = this
        ArrayAdapter.createFromResource(
            this,
            R.array.monate,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner_monate.adapter = adapter
            spinner_monate.prompt="Monate"
        }
        button_berechne = findViewById<Button>(R.id.buttonBerechne)
        //Versuch einfach ein textfeld durch ein query ergebnis zu ersetzen
        tV2 = findViewById(R.id.textView2)

        //tV2.text = transactionViewModel.getMonthSum(2, 2021, "Einnahme").toString()
        tV3 = findViewById(R.id.textView3)
        tV4 = findViewById(R.id.textView5)
        tVInt = findViewById(R.id.editTextNumber)
        val ListNonLD: List<Transaction> = transactionViewModel.allWordsAsNonLiveData()
        button_berechne.setOnClickListener{
            if(!tVInt.text.isEmpty()) {
                val selectedMonthAsInteger = getMonthAsInt(spinner_monate.selectedItem.toString())
                val selectedYear = Integer.parseInt(tVInt.text.toString())
                var summeEinnahmen = 0
                var summeAusgaben = 0
                //Unschöner Workaround
                //val ListNonLD: List<Transaction> = transactionViewModel.allWordsAsNonLiveData() // <--- Falls er rumheult dann zurück in onCreate bzw onResume
                for (i in ListNonLD.indices) {
                    if (ListNonLD.get(i)._jahr == selectedYear) {
                        if (ListNonLD.get(i)._mon == selectedMonthAsInteger) {
                            if (ListNonLD.get(i)._art == "Einnahme") {
                                summeEinnahmen = summeEinnahmen + ListNonLD.get(i)._bet
                            }
                            if (ListNonLD.get(i)._art == "Ausgabe") {
                                summeAusgaben = summeAusgaben + ListNonLD.get(i)._bet
                            }
                        }
                    }
                }
                tV2.text = "Einahmen: " + summeEinnahmen.toString() + " €"
                tV3.text = "Ausgaben: " + summeAusgaben.toString() + " €"
                tV4.text = "Saldo: " + (summeEinnahmen - summeAusgaben).toString() + " €"
            } else {
                Toast.makeText(
                        applicationContext,
                        "Bitte Jahr eintragen",
                        Toast.LENGTH_LONG).show()
            }
        }

        tV7 = findViewById(R.id.textView7)
        spinner_kat = findViewById(R.id.spinner_kat)
        button_berechne2 = findViewById(R.id.buttonBerechne2)

        //Spinner mit großen Array über alle Kategorien füllen
        //val concat = R.array.kategorienAusgaben.plus(R.array.kategorienEinahmen)
        spinner_kat.onItemSelectedListener = this
            ArrayAdapter.createFromResource(
                    this,
                    R.array.alleKats,
                    android.R.layout.simple_spinner_item
            ).also {    adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner_kat.adapter = adapter
                spinner_kat.prompt = "Kategorien"
            }
        button_berechne2.setOnClickListener{
            tV7.text = getSumOfKat(ListNonLD, getMonthAsInt(spinner_monate.selectedItem.toString()), Integer.parseInt(tVInt.text.toString()), spinner_kat.selectedItem.toString()).toString()  + " €"
        }



    }
    //Methode die die Summe des Kategorie wählt im ausgewähltem Monat entspricht
    fun getSumOfKat(list: List<Transaction>, monat: Int, jahr: Int, kat: String) : Int{
        var summe = 0
        for(i in list.indices){
            if(list.get(i)._jahr == jahr){
                if(list.get(i)._mon == monat){
                    if(list.get(i)._kat == kat) {
                        summe = summe + list.get(i)._bet
                    }
                }
            }
        }
        return summe
    }

    //Monat als Int bekommen
    fun getMonthAsInt(monat: String) : Int{
        when(monat) {
            "Januar" -> return 1
            "Februar" -> return 2
            "März" -> return 3
            "April" -> return 4
            "Mai" -> return 5
            "Juni" -> return 6
            "Juli" -> return 7
            "August" -> return 8
            "September" -> return 9
            "Oktober" -> return 10
            "November" -> return 11
            "Dezember" -> return 12
            else -> return 0
        }
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