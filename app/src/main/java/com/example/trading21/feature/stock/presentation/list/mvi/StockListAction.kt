package com.example.trading21.feature.stock.presentation.list.mvi

import com.example.trading21.feature.stock.domain.model.Stock

sealed class StockListAction {
    data object OnBack : StockListAction()

    data class OnSelect(val stock: Stock) : StockListAction()
}