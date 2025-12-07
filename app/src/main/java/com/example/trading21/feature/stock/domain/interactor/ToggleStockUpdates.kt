package com.example.trading21.feature.stock.domain.interactor

import com.example.trading21.feature.stock.datasource.network.StockApiDataSource
import javax.inject.Inject

class ToggleStockUpdates
@Inject
constructor(
    private val stockApiDataSource: StockApiDataSource
) {
    /**
     * Simply for demonstration here we can execute our business logic here
     * reuse it and test it in isolation
     */
    operator fun invoke() {
        if (stockApiDataSource.connectionStatus.value) {
            stockApiDataSource.disconnect()
        } else {
            stockApiDataSource.connect()
        }
    }
}