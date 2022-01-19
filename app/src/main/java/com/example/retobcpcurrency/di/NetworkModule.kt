package com.example.retobcpcurrency.di

import com.example.retobcpcurrency.BuildConfig
import com.example.retobcpcurrency.data.network.CurrencyApi
import com.example.retobcpcurrency.data.network.EndPointService
import com.example.retobcpcurrency.data.network.deserializer.CurrencyDeserializer
import com.example.retobcpcurrency.data.network.response.CurrencyResponse
import com.example.retobcpcurrency.utilities.DEFAULT_TIMEOUT
import com.example.retobcpcurrency.utilities.STRING_CONTENT_TYPE
import com.example.retobcpcurrency.utilities.VAL_CONTENT_TYPE
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Provides
    @Singleton
    fun providerRetrofitBuilder(): Retrofit.Builder = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)

    @Provides
    @Singleton
    fun providerOkHttpBuilder(): OkHttpClient.Builder=
        OkHttpClient.Builder()
            .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor { chain ->
                val request: Request = chain.request().newBuilder()
                    .addHeader(STRING_CONTENT_TYPE,VAL_CONTENT_TYPE)
                    .build()
                chain.proceed(request)
            }

    @Provides
    @Singleton
    fun providerCurrency(retrofit: Retrofit.Builder,
                          okHttpClient: OkHttpClient.Builder,
                         @CurrencyApi gson:Gson,
                         @CurrencyApi interceptor:Interceptor): EndPointService =
        retrofit
            .client(okHttpClient.addInterceptor(interceptor).build())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build().create(EndPointService::class.java)

    @CurrencyApi
    @Provides
    fun providerCurrencyApi():Interceptor {
        val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        date.timeZone = TimeZone.getTimeZone("GMT")
        val now = Calendar.getInstance().time
        val interceptor = Interceptor { chain: Interceptor.Chain ->
            val request = chain.request().newBuilder()
                .addHeader("date-time", date.format(now))
                .build()
            chain.proceed(request)
        }
        return interceptor
    }

    @CurrencyApi
    @Provides
    fun providerGsonCurrency() : Gson = GsonBuilder()
        .registerTypeAdapter(CurrencyResponse::class.java,CurrencyDeserializer())
        .create()



}