package com.example.retobcpcurrency.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.retobcpcurrency.R
import com.example.retobcpcurrency.databinding.ItemCurrencyBinding

class CurrencyAdapter(private val currencyList:ArrayList<ItemCurrency>,private val onClickListener:(ItemCurrency)->Unit) :
    RecyclerView.Adapter<CurrencyViewHolder>() {


    fun updateList(list : List<ItemCurrency>){
        currencyList.clear()
        currencyList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val binding: ItemCurrencyBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),R.layout.item_currency,parent,false)
        return CurrencyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        val item = currencyList[position]
        holder.render(item,onClickListener)
    }

    override fun getItemCount(): Int = currencyList.size

}