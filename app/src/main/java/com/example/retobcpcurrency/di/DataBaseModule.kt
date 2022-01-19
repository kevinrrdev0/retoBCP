package com.example.retobcpcurrency.di

import android.content.Context
import com.example.retobcpcurrency.data.AppDataBase
import com.example.retobcpcurrency.data.dao.CurrencyDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DataBaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context):AppDataBase{
        return AppDataBase.getInstance(context)
    }

    // all Dao
    @Provides
    fun provideCurrencyDao(appDatabase: AppDataBase): CurrencyDao {
        return appDatabase.currencyDao()
    }


}