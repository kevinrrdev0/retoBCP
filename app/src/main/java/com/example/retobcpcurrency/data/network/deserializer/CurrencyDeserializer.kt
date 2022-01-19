package com.example.retobcpcurrency.data.network.deserializer

import com.example.retobcpcurrency.data.network.response.CurrencyResponse
import com.example.retobcpcurrency.data.network.response.Results
import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class CurrencyDeserializer : JsonDeserializer<CurrencyResponse> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ):CurrencyResponse{
        val loginObject = json?.asJsonObject

        var currencyResponse : CurrencyResponse = CurrencyResponse("","",Results(0.0,0.0,0.0,0.0,0.0,0.0,0.0))
            loginObject?.let {
                var errorMsg:String = if (it.has("error"))
                    it.get("error").toString()
                else
                    ""

                currencyResponse = if (!errorMsg.isNotEmpty()){
                    val gson = Gson()
                    val json = it.get("results").asJsonObject
                    val results = gson.fromJson(json, Results::class.java)
                    CurrencyResponse(it.get("base").toString(),"",results);
                }else{
                    CurrencyResponse("",it.get("error").toString(),Results(0.0,0.0,0.0,0.0,0.0,0.0,0.0))

                }
            }
        return currencyResponse


    }
}