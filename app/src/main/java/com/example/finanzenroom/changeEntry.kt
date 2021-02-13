package com.example.finanzenroom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels

class changeEntry : AppCompatActivity() {
    private lateinit var tV7 : TextView
    private lateinit var checkBox : CheckBox
    private lateinit var deleteID : Button
    private lateinit var deleteAll : Button
    private val transactionViewModel: TransactionViewModel by viewModels {
        WordViewModelFactory((application as TransactionApplication).repository)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_entry)
        deleteID = findViewById<Button>(R.id.buttonDelete)
        deleteAll = findViewById<Button>(R.id.buttonDeleteAll)
        checkBox = findViewById<CheckBox>(R.id.checkBox)
        tV7 = findViewById<TextView>(R.id.tV6)
        deleteID.setOnClickListener{
            if(tV7.text.isNotEmpty()){
                transactionViewModel.deleteOne(Integer.parseInt(tV7.text.toString()))
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
            }   else {
                Toast.makeText(
                        applicationContext,
                        "Bitte Haken setzen",
                        Toast.LENGTH_LONG).show()
            }
        }

    }
}