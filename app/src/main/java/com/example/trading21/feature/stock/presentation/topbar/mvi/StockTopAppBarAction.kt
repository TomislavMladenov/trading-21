package com.example.trading21.feature.stock.presentation.topbar.mvi

sealed interface StockTopAppBarAction {
    data object ToggleConnection : StockTopAppBarAction
    data object OnBack
}