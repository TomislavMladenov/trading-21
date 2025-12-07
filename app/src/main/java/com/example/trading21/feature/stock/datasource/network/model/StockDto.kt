package com.example.trading21.feature.stock.datasource.network.model

import kotlinx.serialization.Serializable

@Serializable
data class StockDto(
    val symbol: String,
    val name: String,
    val price: Double
)