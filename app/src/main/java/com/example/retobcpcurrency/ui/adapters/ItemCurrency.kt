package com.example.retobcpcurrency.ui.adapters

import com.example.retobcpcurrency.data.entity.Currency
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ItemCurrency (
    @SerializedName("name") @Expose
    val name: String,
    @SerializedName("currency") @Expose
    val currency: Currency,
    @SerializedName("valor") @Expose
    val valor:Double
        )