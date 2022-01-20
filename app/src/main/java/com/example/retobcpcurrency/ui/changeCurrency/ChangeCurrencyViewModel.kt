package com.example.retobcpcurrency.ui.changeCurrency

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retobcpcurrency.BuildConfig
import com.example.retobcpcurrency.data.local.entity.Currency
import com.example.retobcpcurrency.data.network.response.CurrencyResponse
import com.example.retobcpcurrency.data.network.response.Results
import com.example.retobcpcurrency.repository.CurrencyRepository
import com.example.retobcpcurrency.ui.adapters.ItemCurrency
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangeCurrencyViewModel @Inject constructor(private val repository: CurrencyRepository) :
    ViewModel() {
    var sendMoney = MutableLiveData<String>()
    var purchaseCurrency = MutableLiveData<Double>()
    var saleNameCurrency = MutableLiveData<Double>()
    val currencyList = MutableLiveData<List<Currency>>()
    var versionName = MutableLiveData<String>()
    var sendCurrencies = MutableLiveData<Currency>()
    var getCurrencies = MutableLiveData<Currency>()
    private var listCurrencies = MutableLiveData<List<Currency>>()

    init {
        sendMoney.value = "100.00"
        versionName.value = "Versión " + BuildConfig.VERSION_NAME
        getCurrencyInit()
    }

    fun changeCurrencies() {
        val send = sendMoney.value
        val purchase = purchaseCurrency.value
        if (send != null && send != "" && purchase != null && purchase!! >= 0) {
            sendCurrencies.postValue(getCurrencies.value)
            getCurrencies.postValue(sendCurrencies.value)
            getCurrencies.value?.let { currencyMulti(it.typeCurrency) }
        }
    }

    private fun getCurrencyInit() {
        viewModelScope.launch(Dispatchers.IO) {
            val currencies = repository.getCurrencies()
            currencyList.postValue(currencies)
            listCurrencies.postValue(currencies)
            sendCurrencies.postValue(currencies.first { it.typeCurrency == "USD" })
            getCurrencies.postValue(currencies.first { it.typeCurrency == "PEN" })
            currencyMulti("USD")
        }
    }

    private fun currencyMulti(from: String) = viewModelScope.launch(Dispatchers.IO) {
        repository.getCurrencyMulti(from, "EUR,USD,JPY,GBP,CHF,CAD,PEN").let {
           if (it.isSuccessful){
               val results: Results? = it.body()?.results
               var valueCurrency: Double
               when (getCurrencies.value!!.typeCurrency) {
                   "EUR" -> {
                       valueCurrency = results?.EUR!!
                   }
                   "USD" -> {
                       valueCurrency = results?.USD!!
                   }
                   "JPY" -> {
                       valueCurrency = results?.JPY!!
                   }
                   "GBP" -> {
                       valueCurrency = results?.GBP!!
                   }
                   "CHF" -> {
                       valueCurrency = results?.CHF!!
                   }
                   "CAD" -> {
                       valueCurrency = results?.CAD!!
                   }
                   "PEN" -> {
                       valueCurrency = results?.PEN!!
                   }
                   else -> {
                       valueCurrency = 0.0
                   }
               }
               purchaseCurrency.postValue(valueCurrency)
               saleNameCurrency.postValue(valueCurrency + 0.33)
           }
        }
    }

    fun getCurrencyMulti(from: String,to :String) = viewModelScope.launch(Dispatchers.IO) {
        repository.getCurrencyMulti(from, "EUR,USD,JPY,GBP,CHF,CAD,PEN").let {
            if (it.isSuccessful){
                val results: Results? = it.body()?.results
                var valueCurrency: Double
                when (to) {
                    "EUR" -> {
                        valueCurrency = results?.EUR!!
                    }
                    "USD" -> {
                        valueCurrency = results?.USD!!
                    }
                    "JPY" -> {
                        valueCurrency = results?.JPY!!
                    }
                    "GBP" -> {
                        valueCurrency = results?.GBP!!
                    }
                    "CHF" -> {
                        valueCurrency = results?.CHF!!
                    }
                    "CAD" -> {
                        valueCurrency = results?.CAD!!
                    }
                    "PEN" -> {
                        valueCurrency = results?.PEN!!
                    }
                    else -> {
                        valueCurrency = 0.0
                    }
                }
                purchaseCurrency.postValue(valueCurrency)
                saleNameCurrency.postValue(valueCurrency + 0.22)
            }
        }
    }
    fun sendCurrencyMulti(from: String,to :String) = viewModelScope.launch(Dispatchers.IO) {
        repository.getCurrencyMulti(from, "EUR,USD,JPY,GBP,CHF,CAD,PEN").let {
            val results: Results? = it.body()?.results
            var valueCurrency: Double
            when (to) {
                "EUR" -> {
                    valueCurrency = results?.EUR!!
                }
                "USD" -> {
                    valueCurrency = results?.USD!!
                }
                "JPY" -> {
                    valueCurrency = results?.JPY!!
                }
                "GBP" -> {
                    valueCurrency = results?.GBP!!
                }
                "CHF" -> {
                    valueCurrency = results?.CHF!!
                }
                "CAD" -> {
                    valueCurrency = results?.CAD!!
                }
                "PEN" -> {
                    valueCurrency = results?.PEN!!
                }
                else -> {
                    valueCurrency = 0.0
                }
            }
            purchaseCurrency.postValue(valueCurrency)
            saleNameCurrency.postValue(valueCurrency + 0.22)
        }
    }
}