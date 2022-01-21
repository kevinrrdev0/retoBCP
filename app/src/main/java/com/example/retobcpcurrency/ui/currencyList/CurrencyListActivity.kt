package com.example.retobcpcurrency.ui.currencyList

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.retobcpcurrency.R
import com.example.retobcpcurrency.databinding.ActivityCurrencyListBinding
import com.example.retobcpcurrency.ui.adapters.CurrencyAdapter
import com.example.retobcpcurrency.ui.adapters.ItemCurrency
import com.example.retobcpcurrency.utilities.FROM
import com.example.retobcpcurrency.utilities.TO
import com.example.retobcpcurrency.utilities.TYPE
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class CurrencyListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCurrencyListBinding
    private val viewModel:CurrencyListViewModel by viewModels()
    private var type: String? = null
    private var send: String? = null
    private var get: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_currency_list)
        binding.lifecycleOwner = this
        initActivity()     
        viewModel.getList.observe(this, {
            if (it.isNotEmpty()
            ){
                binding.rvCurrency.apply {
                    layoutManager = LinearLayoutManager(this@CurrencyListActivity)
                    adapter =  CurrencyAdapter(it!! as ArrayList<ItemCurrency>){ yolo->
                        onItemSelected(yolo)
                    }
                }
            }
        })
       lifecycleScope.launchWhenStarted {
           viewModel.viewState.collect {
               binding.pbRv.visibility = if (it.loading) View.VISIBLE else View.GONE
           }
       }
    }

    private fun initActivity() {
        type = intent.getStringExtra(TYPE)
        send = intent.getStringExtra(FROM)
        get = intent.getStringExtra(TO)
        when(type) {
            "send" -> {
                viewModel.from.value = send
                viewModel.getCurrencyList(send!!)

            }
            "get" -> {
                viewModel.from.value = get
                viewModel.getCurrencyList(get!!)
            }
        }
    }

    private fun onItemSelected(item:ItemCurrency){
        val data = Intent()
        data.putExtra(TYPE, type)
        data.putExtra(FROM, item.name)
        data.putExtra(TO, item.currency.typeCurrency)
        setResult(RESULT_OK, data)
        finish()
    }

}