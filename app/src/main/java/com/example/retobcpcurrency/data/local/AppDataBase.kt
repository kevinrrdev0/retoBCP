package com.example.retobcpcurrency.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.retobcpcurrency.data.local.dao.CurrencyDao
import com.example.retobcpcurrency.data.local.entity.Currency
import com.example.retobcpcurrency.utilities.CURRENCY_DATA_FILENAME
import com.example.retobcpcurrency.utilities.DATABASE_NAME
import com.example.retobcpcurrency.workers.InitDatabaseWorker
import com.example.retobcpcurrency.workers.InitDatabaseWorker.Companion.KEY_FILENAME

@Database(entities = [Currency::class], version = 1)
abstract class AppDataBase: RoomDatabase(){

    abstract fun currencyDao(): CurrencyDao

    companion object{
        @Volatile private var instance: AppDataBase? =null

        fun getInstance(context: Context): AppDataBase {
            return instance ?: synchronized(this){
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDataBase {
            return Room.databaseBuilder(context, AppDataBase::class.java, DATABASE_NAME)
                .addCallback(
                    object : RoomDatabase.Callback(){
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            val request = OneTimeWorkRequestBuilder<InitDatabaseWorker>()
                                .setInputData(workDataOf(KEY_FILENAME to CURRENCY_DATA_FILENAME))
                                .build()
                            WorkManager.getInstance(context).enqueue(request)
                        }
                    }
                )
                .build()
        }

    }
}