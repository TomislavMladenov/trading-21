package com.example.trading21.feature.stock.datasource.network

import com.example.trading21.base.core.util.DispatcherProvider
import com.example.trading21.feature.stock.datasource.network.model.StockDto
import com.example.trading21.feature.stock.datasource.network.model.fromDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.serialization.json.Json

class FakeTestStockApiDataSource(
    dispatcher: DispatcherProvider
) : StockApiDataSource {

    private val jsonParser: Json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        encodeDefaults = true
        explicitNulls = false
    }

    private val fakeDataProvider = FakeDataProvider(dispatcher, jsonParser)

    private val _connectionStatus = MutableStateFlow(false)
    override val connectionStatus: StateFlow<Boolean> = _connectionStatus.asStateFlow()

    private val _stocks = MutableStateFlow(fakeDataProvider.generateInitialStocks())
    override val stocks = _stocks.asStateFlow()

    override fun connect() {
        _connectionStatus.value = true

        fakeDataProvider.startSimulation(stocks.value) { text ->
            val dto = jsonParser.decodeFromString<List<StockDto>>(text)
            _stocks.value = stocks.value.fromDto(dto)
        }
    }

    override fun disconnect() {
        _connectionStatus.value = false
        fakeDataProvider.stopSimulation()
    }
}