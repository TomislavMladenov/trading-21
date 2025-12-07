package com.example.trading21.feature.stock.domain.interactor

import com.example.trading21.feature.stock.datasource.network.StockApiDataSource
import javax.inject.Inject

class ObserveStockList
@Inject
constructor(
    private val stockApiDataSource: StockApiDataSource
) {
    operator fun invoke() = stockApiDataSource.stocks
}