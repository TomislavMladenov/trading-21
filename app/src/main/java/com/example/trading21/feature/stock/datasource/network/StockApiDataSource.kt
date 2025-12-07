package com.example.trading21.feature.stock.datasource.network

import com.example.trading21.feature.stock.domain.model.Stock
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface StockApiDataSource {

    val stocks: Flow<List<Stock>>

    val connectionStatus: StateFlow<Boolean>

    fun connect()

    fun disconnect()

}