package com.example.finanzenroom

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

// Annotates class to be a Room Database with a table (entity) of the Word class
@Database(entities = arrayOf(Transaction::class), version = 3, exportSchema = false)
public abstract class TransactionRoomDatabase : RoomDatabase() {

    abstract fun transactionDao(): TransactionDao

    private class TransactionDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.transactionDao())
                }
            }
        }

        suspend fun populateDatabase(transactionDao: TransactionDao) {
            // Delete all content here.
            transactionDao.deleteAll()

            // Add sample words.
            var transaction = Transaction(0, "Ausgabe", "Essen", 2021, 1, 24, 420)
            transactionDao.insert(transaction)
            var transaction2 = Transaction(0, "Ausgabe", "Essen", 2021, 2, 14, 69)
            transactionDao.insert(transaction2)
            //word = Word("World!")
            //wordDao.insert(word)

            // TODO: Add your own words!
        }
    }

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: TransactionRoomDatabase? = null

        fun getDatabase(
                context: Context,
                scope: CoroutineScope
            ): TransactionRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TransactionRoomDatabase::class.java,
                    "transaction_database"
                )
                        .fallbackToDestructiveMigration()
                    .addCallback((TransactionDatabaseCallback(scope)))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }


}