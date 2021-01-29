package com.example.finanzenroom

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class TransactionListAdapter : ListAdapter<Transaction, TransactionListAdapter.TransactionViewHolder>(WordsComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        return TransactionViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val current = getItem(position)
        Log.e("Laenge", current._dat.toString().length.toString())
        if(Integer.parseInt(current._dat.toString().length.toString()) > 7) { // <-------------------------------------------------------------- IF NUR DA DAMIT ES LÄDT WEIL NOCH DATUMS MIT WENIEGR ALS 8 BUCHSTABEN GESPEICHERT SIN, NACH LÖSCHEN EGAL
            val jahr = current._dat.toString().substring(0,3).toString()
            val monat = current._dat.toString().substring(5,6).toString()
            val tag = current._dat.toString().substring(6,7).toString()
            val datumzsm = "$tag.$monat.$jahr"
            holder.bind( "hallo" + current._art + " " + current._kat + " " + current._bet ) // Edit hier um Anzeige zu ändern    ------------> Also hier geht er zwar rein aber er kann dieses datum nicht zusammenstellen weil die variablen zur berechnung von tag monat und jahr komischerweise leer sind
        } else {
            holder.bind( current._dat.toString() + current._art + " " + current._kat + " " + current._bet ) // Edit hier um Anzeige zu ändern
        }

        //val tag = current._dat.toString().substring(0,3).toString()
        //val monat = current._dat.toString().substring(3,6).toString()
        //val jahr = current._dat.toString().substring(6,7).toString()
        //val datumzsm = "$tag.$monat.$jahr"
        holder.bind( " " + current._art + " " + current._kat + " " + current._bet ) // Edit hier um Anzeige zu ändern
    }

    class TransactionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val transactionItemView: TextView = itemView.findViewById(R.id.textView)

        fun bind(text: String) {
            transactionItemView.text = text.toString()
        }

        companion object {
            fun create(parent: ViewGroup): TransactionViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_item, parent, false)
                return TransactionViewHolder(view)
            }
        }
    }

    class WordsComparator : DiffUtil.ItemCallback<Transaction>() {
        override fun areItemsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
            return oldItem._id == newItem._id
        }
    }
}