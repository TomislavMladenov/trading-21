package com.example.trading21.feature.stock.presentation.list.mvi

import com.example.trading21.feature.stock.domain.model.Stock

data class StockListState(
    val stocks: List<Stock> = emptyList(),
    val isLoading: Boolean = false
)