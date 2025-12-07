package com.example.trading21.feature.stockdetails.mvi

sealed class StockDetailsAction {
    data object OnBack : StockDetailsAction()
}