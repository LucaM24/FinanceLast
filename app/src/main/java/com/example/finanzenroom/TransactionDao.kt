package com.example.finanzenroom

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao{

        @Query("SELECT * FROM transactions_table")
        fun getAll(): Flow<List<Transaction>>

        @Insert(onConflict = OnConflictStrategy.IGNORE)
        suspend fun insert(transaction: Transaction)

        @Query("DELETE FROM transactions_table")
        suspend fun deleteAll()

        @Query("SELECT SUM(_bet) FROM transactions_table WHERE _mon = (:monat) AND _jahr = (:jahr) AND _art = (:art)")
        suspend fun getMonthSum(monat: Int, jahr: Int, art: String): Int

}