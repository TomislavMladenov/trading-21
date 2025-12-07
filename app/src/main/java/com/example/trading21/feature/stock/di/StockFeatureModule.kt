package com.example.trading21.feature.stock.di

import com.example.trading21.base.core.util.DispatcherProvider
import com.example.trading21.feature.stock.datasource.network.FakeStockApiDataSource
import com.example.trading21.feature.stock.datasource.network.StockApiDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class StockFeatureModule {

    @Provides
    @Singleton
    fun provideStockApiDataSource(
        dispatcher: DispatcherProvider,
    ): StockApiDataSource = FakeStockApiDataSource(dispatcher)
}