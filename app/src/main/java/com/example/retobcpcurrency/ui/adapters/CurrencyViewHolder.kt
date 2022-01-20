package com.example.retobcpcurrency.ui.adapters

import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.retobcpcurrency.R
import com.example.retobcpcurrency.databinding.ItemCurrencyBinding

class CurrencyViewHolder(private val binding : ItemCurrencyBinding): RecyclerView.ViewHolder(binding.root) {


    fun render(item : ItemCurrency,onClickListener:(ItemCurrency)->Unit){
        binding.itemCurrency = item
        Glide.with(binding.ivFlag.context).load(item.currency.pathImage).into(binding.ivFlag)
        itemView.setOnClickListener{
           onClickListener(item)
        }


    }


}