package com.example.retobcpcurrency.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.retobcpcurrency.data.entity.Currency

@Dao
interface CurrencyDao : BaseDao<Currency>{
    /**
     * Get all data from the Data table.
     */
    @Query("SELECT * FROM currency")
    suspend fun getData(): List<Currency>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(plants: List<Currency>)
}