package com.example.trading21.feature.stock.domain.model

data class Stock(
    val symbol: String,
    val name: String,
    val price: Double,
    val previousPrice: Double = 0.0,
    val description: String = "A leading technology company."
) {
    val isRising: Boolean get() = price >= previousPrice
}