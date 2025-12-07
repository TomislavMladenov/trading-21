package com.example.trading21.feature.stocklist.mvi

sealed class StockListAction {
    data object OnBack : StockListAction()
}