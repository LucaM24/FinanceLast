package com.example.finanzenroom

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.activity.viewModels
import org.w3c.dom.Text

class changeEntry : AppCompatActivity() {
    private lateinit var tV7 : TextView
    private lateinit var checkBox : CheckBox
    private lateinit var deleteID : ImageButton
    private lateinit var deleteAll : Button
    private lateinit var changeEntryÜberschrift: TextView
    private val transactionViewModel: TransactionViewModel by viewModels {
        WordViewModelFactory((application as TransactionApplication).repository)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_entry)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) //Dadurch wird in der Actionbar ein Pfeil angezeigt, mit dem man zum Hauptbildschirm, (oder auf ein beliebiges), wechseln kann


        changeEntryÜberschrift = findViewById(R.id.changeentryüberschrift)
        changeEntryÜberschrift.text = "Eintrag löschen"

        deleteID = findViewById<ImageButton>(R.id.buttonDelete)
        deleteAll = findViewById<Button>(R.id.buttonDeleteAll)
        checkBox = findViewById<CheckBox>(R.id.checkBox)
        tV7 = findViewById<TextView>(R.id.tV6)
        deleteID.setOnClickListener{
            if(tV7.text.isNotEmpty()){
                transactionViewModel.deleteOne(Integer.parseInt(tV7.text.toString()))
                val intent = Intent(this@changeEntry, MainActivity::class.java) //Zurück zum HomeScreen
                startActivity(intent)
            } else {
                Toast.makeText(
                        applicationContext,
                        "Bitte ID eingeben",
                        Toast.LENGTH_LONG).show()
            }
        }

        deleteAll.setOnClickListener{
            if(checkBox.isChecked){
                transactionViewModel.deleteAll()
                val intent = Intent(this@changeEntry, MainActivity::class.java) //Zurück zum HomeScreen
                startActivity(intent)
            }   else {
                Toast.makeText(
                        applicationContext,
                        "Bitte Haken setzen",
                        Toast.LENGTH_LONG).show()
            }
        }

    }
}