package com.example.trading21.feature.stock.presentation.details.mvi

sealed class StockDetailsAction {
    data object OnBack : StockDetailsAction()
}