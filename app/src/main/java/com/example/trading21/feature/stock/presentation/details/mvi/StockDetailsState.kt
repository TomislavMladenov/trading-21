package com.example.trading21.feature.stock.presentation.details.mvi

import com.example.trading21.feature.stock.domain.model.Stock

sealed interface SelectedStock {
    data class Available(val stock: Stock) : SelectedStock
    object Initial : SelectedStock
    object NotFound : SelectedStock
}

data class StockDetailsState(
    val isLoading: Boolean = false,
    val selectedStock: SelectedStock = SelectedStock.Initial,
)