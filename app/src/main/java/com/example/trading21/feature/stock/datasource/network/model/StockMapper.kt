package com.example.trading21.feature.stock.datasource.network.model

import com.example.trading21.feature.stock.domain.model.Stock

fun List<Stock>.fromDto(dtoList: List<StockDto>) = this.map { domainStock ->
    dtoList.find { it.symbol == domainStock.symbol }?.let { domainStock.fromDto(it) }
        ?: return@map domainStock
}

fun Stock.fromDto(dto: StockDto) = this.copy(
    name = dto.name,
    previousPrice = this.price,
    price = dto.price
)
