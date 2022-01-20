package com.example.retobcpcurrency.ui.changeCurrency

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.retobcpcurrency.R
import com.example.retobcpcurrency.databinding.ActivityChangeCurrencyBinding
import com.example.retobcpcurrency.ui.currencyList.CurrencyListActivity
import com.example.retobcpcurrency.utilities.FROM
import com.example.retobcpcurrency.utilities.TO
import com.example.retobcpcurrency.utilities.TYPE
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
        binding.btnNameCurrency1.setOnLongClickListener {
            launchIntent("send")
            true
        }
        binding.btnNameCurrency2.setOnLongClickListener {
            launchIntent("get")
            true
        }
    }
    private fun launchIntent(type:String){
        val intent = Intent(this@ChangeCurrencyActivity, CurrencyListActivity::class.java)
        intent.putExtra(TYPE, type)
        intent.putExtra(FROM, viewModel.getCurrencies.value!!.typeCurrency)
        intent.putExtra(TO, viewModel.sendCurrencies.value!!.typeCurrency)
        resultLauncherListCurrencies.launch(intent)
    }

    private var resultLauncherListCurrencies = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.let {
                val from = it.getStringExtra(FROM)
                val to = it.getStringExtra(TO)
                when(it.getStringExtra(TYPE)){
                    "send" -> {
                      viewModel.sendCurrencies.value = viewModel.currencyList.value!!.first {
                                c-> c.typeCurrency == to
                      }
                        viewModel.sendCurrencyMulti(to!!,from!!)
                    }
                    "get" -> {
                        viewModel.getCurrencies.value = viewModel.currencyList.value!!.first {
                                c-> c.typeCurrency == to
                        }
                        viewModel.getCurrencyMulti(from!!,to!!)
                    }
                    else -> {

                    }
                }


            }


//            updateExchangeRate(typeExchange)
        }
    }
}