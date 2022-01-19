package com.example.retobcpcurrency.repository

import com.example.retobcpcurrency.BuildConfig
import com.example.retobcpcurrency.data.dao.CurrencyDao
import com.example.retobcpcurrency.data.entity.Currency
import com.example.retobcpcurrency.data.network.EndPointService
import javax.inject.Inject

class CurrencyRepository @Inject constructor(private val currencyApi: EndPointService,private val currencyDao: CurrencyDao) {

    suspend fun getCurrencyMulti(from :String,to:String, apiKey :String = BuildConfig.API_KEY_FOREST )
        = currencyApi.getCurrencyMulti(from,to,apiKey)

    suspend fun getCurrencies():List<Currency> = currencyDao.getData()
}