package com.example.retobcpcurrency.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.retobcpcurrency.data.local.AppDataBase
import com.example.retobcpcurrency.data.local.entity.Currency
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class InitDatabaseWorker(context: Context, workerParameters: WorkerParameters) : CoroutineWorker(context,workerParameters) {
    override suspend fun doWork(): Result = withContext(Dispatchers.IO){
        try {
            val filename = inputData.getString(KEY_FILENAME)
            if (filename != null) {
                applicationContext.assets.open(filename).use { inputStream ->
                    com.google.gson.stream.JsonReader(inputStream.reader()).use { jsonReader ->
                        val currencyType = object : TypeToken<List<Currency>>() {}.type
                        val currencyList: List<Currency> = Gson().fromJson(jsonReader, currencyType)

                        val database = AppDataBase.getInstance(applicationContext)
                        database.currencyDao().insertAll(currencyList)

                        Result.success()
                    }
                }
            } else {
                Log.e(TAG, "Error seeding database - no valid filename")
                Result.failure()
            }
        } catch (ex: Exception) {
            Log.e(TAG, "Error seeding database", ex)
            Result.failure()
        }
        Result.success()
    }
    companion object {
        private const val TAG = "InitDatabaseWorker"
        const val KEY_FILENAME = "PLANT_DATA_FILENAME"
    }
}