package com.example.retobcpcurrency.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currency")
data class Currency (
    @PrimaryKey(autoGenerate = true) val id:Int,
    @ColumnInfo(name = "country_name") val countryName: String,
    @ColumnInfo(name = "path_image") val pathImage: String,
    @ColumnInfo(name = "type_currency") val typeCurrency:String,
    @ColumnInfo(name = "name_currency") val nameCurrency:String

)