package com.example.finanzenroom

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private val newTransactionActivityRequestCode = 1
    private val transactionViewModel: TransactionViewModel by viewModels {
        WordViewModelFactory((application as TransactionApplication).repository)
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

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this@MainActivity, NeuerEintragActivity::class.java)
            startActivityForResult(intent, newTransactionActivityRequestCode)
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