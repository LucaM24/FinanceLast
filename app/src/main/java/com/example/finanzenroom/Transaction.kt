package com.example.finanzenroom
import android.os.Parcelable
import android.text.InputFilter
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions_table")
class Transaction(@PrimaryKey(autoGenerate = true) val _id: Int, val _art: String, val _kat: String, val _jahr: Int, val _mon: Int, val _tag: Int, val _bet: Int)
