package com.example.finanzenroom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class Stats : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private lateinit var spinner_monate : Spinner
    private lateinit var button_berechne : Button
    private lateinit var tV2 : TextView
    private lateinit var tV3 : TextView
    private lateinit var tV4 : TextView
    private lateinit var tVInt : TextView


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
        button_berechne.setOnClickListener{
            val selectedMonthAsInteger = getMonthAsInt(spinner_monate.selectedItem.toString())
            lifecycleScope.launch {
                tV2.text = transactionViewModel.getMonthSum(2, 2021, "Einnahme").toString()
                //tV2.text =
                //    transactionViewModel.getMonthSum(
                //        selectedMonthAsInteger,
                //        Integer.parseInt(tVInt.text.toString()),
                //        "Einnahme"
                ///    ).toString()
                //tV3.text =
                //    transactionViewModel.getMonthSum(
                 //       selectedMonthAsInteger,
                 //       Integer.parseInt(tVInt.text.toString()),
                 //       "Ausgabe"
                 //   ).toString()
                //tV4.text =
                //    ((Integer.parseInt(tV2.text.toString()) - Integer.parseInt(tV3.text.toString())).toString())
            }
        }



    }

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