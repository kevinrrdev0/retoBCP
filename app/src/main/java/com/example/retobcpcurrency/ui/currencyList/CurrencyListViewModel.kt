package com.example.retobcpcurrency.ui.currencyList

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retobcpcurrency.data.local.entity.Currency
import com.example.retobcpcurrency.data.network.response.CurrencyResponse
import com.example.retobcpcurrency.data.network.response.Results
import com.example.retobcpcurrency.repository.CurrencyRepository
import com.example.retobcpcurrency.ui.adapters.ItemCurrency
import com.example.retobcpcurrency.utilities.VAL_MULTIPLE
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrencyListViewModel @Inject constructor(private val repository: CurrencyRepository) :
    ViewModel() {
    private var listCurrencies = MutableLiveData<List<Currency>>()
    var listItemCurrency = MutableLiveData<List<ItemCurrency>>()
    val getList: LiveData<List<ItemCurrency>> get() = listItemCurrency

    var from = MutableLiveData<String>()
    private val _viewState = MutableStateFlow(CurrencyListViewState())
    val viewState: StateFlow<CurrencyListViewState>
        get() = _viewState

    fun getCurrencyList(from: String) = viewModelScope.launch(Dispatchers.IO) {
        _viewState.value = CurrencyListViewState(true)
        val currencies = repository.getCurrencies()
        listCurrencies.postValue(currencies)
        repository.getCurrencyMulti(from, VAL_MULTIPLE).let {
            if (it.isSuccessful) {
                val list = mutableListOf<ItemCurrency>()
                val results: Results? = it.body()?.results

                for (item in listCurrencies.value!!) {
                    var valueCurrency: Double
                    when (item.typeCurrency) {
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
                    list.add(ItemCurrency(from, item, valueCurrency))
                }
                listItemCurrency.postValue(list)
            } else {
                val gson = Gson()
                val type = object : TypeToken<CurrencyResponse>() {}.type
                val errorResponse: CurrencyResponse? = gson.fromJson(it.errorBody()?.string(), type)
            }
            _viewState.value = CurrencyListViewState(false)
        }
    }

}