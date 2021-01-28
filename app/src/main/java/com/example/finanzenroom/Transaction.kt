package com.example.finanzenroom

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions_table")
data class Transaction(@PrimaryKey(autoGenerate = true) val _id: Int, val _art: String, val _kat: String, val _dat: Int, val _bet: Int)