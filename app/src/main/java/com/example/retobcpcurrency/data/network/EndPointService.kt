package com.example.retobcpcurrency.data.network

import com.example.retobcpcurrency.data.network.response.CurrencyResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface EndPointService {

    @GET(GET_FETCH_MULTI)
    suspend fun getCurrencyMulti(@Query("from") from:String,
                                 @Query("to") to:String,
                                 @Query("api_key") apiKey : String):Response<CurrencyResponse>

}