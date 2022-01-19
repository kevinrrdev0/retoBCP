package com.example.retobcpcurrency.ui.changeCurrency

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.retobcpcurrency.R
import com.example.retobcpcurrency.databinding.ActivityChangeCurrencyBinding
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangeCurrencyActivity : AppCompatActivity() {

    private val viewModel:ChangeCurrencyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityChangeCurrencyBinding = DataBindingUtil.setContentView(this,R.layout.activity_change_currency)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.btnChangeCurrency.setOnClickListener { viewModel.changeCurrencies() }
    }
    var resultLauncherListCurrencies = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
//            val newCurrencyFrom =
//            val newCurrencyTo = Gson().fromJson(result.data?.getStringExtra(NEW_CURRENCY_TO), CurrencyLocalModel::class.java)
//            val data = Gson().fromJson(result.data?.getStringExtra(DATA), ApiResponse::class.java)
//
//            typeExchange = data.result?.get(newCurrencyTo.currency.code)?.asDouble ?: 0.0
//            binding.displayConvertionComponent.setValuePurchaseAndSell(typeExchange)
//            binding.displayConvertionComponent.setCurrrencies(newCurrencyFrom, newCurrencyTo)
//            updateExchangeRate(typeExchange)
        }
    }
}