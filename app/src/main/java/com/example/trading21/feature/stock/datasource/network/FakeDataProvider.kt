package com.example.trading21.feature.stock.datasource.network

import com.example.trading21.base.core.util.DispatcherProvider
import com.example.trading21.feature.stock.datasource.network.model.StockDto
import com.example.trading21.feature.stock.domain.model.Stock
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlin.random.Random

class FakeDataProvider(
    dispatcher: DispatcherProvider,
    private val jsonParser: Json
) {
    private var simulationJob: Job? = null
    private val scope = CoroutineScope(SupervisorJob() + dispatcher.io())

    fun startSimulation(
        stocks: List<Stock>,
        onSend: (String) -> Unit,
    ) {
        stopSimulation()
        simulationJob = scope.launch {
            while (isActive) {
                // Pick a random stock to update every small interval or batch update
                // Requirement: Update each symbol every 2 seconds.
                // We simulate this by iterating the list.
                val currentList = stocks
                currentList.forEach { stock ->
                    val newPrice = stock.price + Random.nextDouble(-7.0, 7.0)

                    val dto = StockDto(
                        symbol = stock.symbol,
                        name = stock.name,
                        price = newPrice
                    )
                    val jsonString = jsonParser.encodeToString(dto)
                    onSend(jsonString)
                    delay(100) // Simulate network delay
                }
                delay(2000) // Wait before next cycle
            }
        }
    }

    fun stopSimulation() {
        simulationJob?.cancel()
    }

    fun generateInitialStocks(): List<Stock> {
        val symbols = listOf("AAPL", "GOOG", "MSFT", "AMZN", "TSLA", "NVDA", "META", "NFLX", "AMD", "INTC",
            "IBM", "ORCL", "CRM", "ADBE", "CSCO", "QCOM", "TXN", "AVGO", "SHOP", "SQ",
            "UBER", "ABNB", "PYPL", "SPOT", "SNAP")
        return symbols.map {
            Stock(it, "$it Inc.", Random.nextDouble(100.0, 2000.0))
        }
    }
}