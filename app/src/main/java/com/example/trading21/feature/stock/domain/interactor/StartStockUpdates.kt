package com.example.trading21.feature.stock.domain.interactor

import com.example.trading21.feature.stock.datasource.network.StockApiDataSource
import javax.inject.Inject

class StartStockUpdates
@Inject
constructor(
    private val stockApiDataSource: StockApiDataSource
) {
    operator fun invoke() {
        if (stockApiDataSource.connectionStatus.value) {
            // Already connected show user a message or simply ignore
        } else {
            stockApiDataSource.connect()
        }
    }
}
