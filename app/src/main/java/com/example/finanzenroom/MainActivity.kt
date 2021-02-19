package com.example.finanzenroom

import android.app.Activity
import android.content.ClipData
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*

class MainActivity : AppCompatActivity() {
    private val newTransactionActivityRequestCode = 1
    private val transactionViewModel: TransactionViewModel by viewModels {
        WordViewModelFactory((application as TransactionApplication).repository)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean { //3 Punkte in der Leiste für Einstellungen
        menuInflater.inflate(R.menu.nav_menu,menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean { //und der ActionListener dazu
        val intent = Intent(this@MainActivity, Settings::class.java)
        startActivity(intent)
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = TransactionListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        transactionViewModel.allWords.observe(this, Observer { transaction ->
            // Update the cached copy of the words in the adapter.
            transaction?.let { adapter.submitList(it) }
        })

        /*Optional Datum anzeigen-> Wenn ja Textview muss noch auf XML
        var datumTextView = findViewById<TextView>(R.id.datumtextview)
        var c = Calendar.getInstance()
        var year = c.get(Calendar.YEAR)
        var month = c.get(Calendar.MONTH) + 1
        var day = c.get(Calendar.DAY_OF_MONTH)
        datumTextView.text = ""+day+""+"."+month+"."+year
         */

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this@MainActivity, NeuerEintragActivity::class.java)
            startActivityForResult(intent, newTransactionActivityRequestCode)
        }
        val fab2 = findViewById<FloatingActionButton>(R.id.fab2)
        fab2.setOnClickListener{
            val intent = Intent(this@MainActivity, Stats::class.java)
            startActivity(intent)
        }
        val fab3 = findViewById<FloatingActionButton>(R.id.fab3)
        fab3.setOnClickListener{
            val intent = Intent(this@MainActivity, changeEntry::class.java)
            startActivity(intent)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == newTransactionActivityRequestCode && resultCode == Activity.RESULT_OK) {
            data?.getStringExtra(NeuerEintragActivity.EXTRA_REPLY)?.let {
                //val bundle: Bundle? = intent.extras
                //val betrag2 = Integer.parseInt(bundle?.get(NeuerEintragActivity.betrag) as String)
                //val tag2 = Integer.parseInt(bundle?.get(NeuerEintragActivity.tag).toString())
                //val kat2 = bundle?.get(NeuerEintragActivity.kat)
                //val art2 = bundle?.get(NeuerEintragActivity.art)
                var betrag = Integer.parseInt(data.getStringExtra(NeuerEintragActivity.betrag))
                //var tag = Integer.parseInt(data.getStringExtra(NeuerEintragActivity.tag))
                var tag = data.getIntExtra(NeuerEintragActivity.tag, 0)
                var monat = data.getIntExtra(NeuerEintragActivity.monat, 0)
                var jahr = data.getIntExtra(NeuerEintragActivity.jahr, 0)
                var kat = data.getStringExtra(NeuerEintragActivity.kat).toString()
                var art = data.getStringExtra(NeuerEintragActivity.art).toString()
                var transaction = Transaction(0, art, kat, jahr, monat, tag, betrag)
                transactionViewModel.insert(transaction)
            }
        } else {
            Toast.makeText(
                applicationContext,
                R.string.empty_not_saved,
                Toast.LENGTH_LONG).show()
        }
    }
}