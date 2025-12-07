package com.example.trading21.feature.stock.presentation.details.mvi

sealed interface StockDetailsAction {

    data object OnBack : StockDetailsAction
}