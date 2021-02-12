package com.example.finanzenroom

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class TransactionRepository(private val transactionDao: TransactionDao) {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val allWords: Flow<List<Transaction>> = transactionDao.getAll()

    val allWordsNonFD : List<Transaction> = transactionDao.getAllNonLD()



    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(transaction: Transaction) {
        transactionDao.insert(transaction)
    }

    @Suppress("RedundantSuspenModifier")
    @WorkerThread
    suspend fun getAllFromMonth(monat: Int, jahr: Int, art: String){
        val allAusgabenFromMonth: Flow<List<Transaction>> = transactionDao.getAllFromMonth(monat, jahr, "Ausgabe")
        val allEinnahmenFromMonth: Flow<List<Transaction>> = transactionDao.getAllFromMonth(monat, jahr, "Einnahme")
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getMonthSum(monat: Int, jahr: Int, art: String) {
        transactionDao.getMonthSum(monat, jahr, art)
    }

    fun deleteOne(id: Int){
        transactionDao.deleteOne(id)
    }

    fun deleteAll(){
        transactionDao.deleteAll()
    }
}