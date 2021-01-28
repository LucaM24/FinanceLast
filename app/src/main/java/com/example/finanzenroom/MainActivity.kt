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
                var betrag = intent.getIntExtra(NeuerEintragActivity.betrag, 0)
                var tag = intent.getIntExtra(NeuerEintragActivity.tag, 0)
                var kat = intent.getStringExtra(NeuerEintragActivity.kat).toString()
                var art = intent.getStringExtra(NeuerEintragActivity.art).toString()
                var transaction = Transaction(0, art, kat, tag, betrag)
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