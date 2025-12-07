package com.example.trading21.feature.stock.datasource.network

import com.example.trading21.base.core.util.DispatcherProvider
import com.example.trading21.base.datasource.network.util.NetworkConstants.STOCKS_URL
import com.example.trading21.feature.stock.datasource.network.model.StockDto
import com.example.trading21.feature.stock.domain.model.Stock
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener

class FakeStockApiDataSource(
    dispatcher: DispatcherProvider,
) : StockApiDataSource {

    private val jsonParser: Json = Json { ignoreUnknownKeys = true }
    private val dataProvider: FakeDataProvider = FakeDataProvider(dispatcher, jsonParser)
    private val _stocks = MutableStateFlow(dataProvider.generateInitialStocks())
    override val stocks = _stocks.asStateFlow()

    private val _connectionStatus = MutableStateFlow(false)
    override val connectionStatus: StateFlow<Boolean> = _connectionStatus.asStateFlow()
    private val client = OkHttpClient()
    private var webSocket: WebSocket? = null

    override fun connect() {
        val request = Request.Builder().url(STOCKS_URL).build()
        webSocket = client.newWebSocket(request, object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                _connectionStatus.value = true
                dataProvider.startSimulation(stocks.value) {
                    webSocket.send(it)
                }
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                handleMessage(text)
            }

            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                _connectionStatus.value = false
                dataProvider.stopSimulation()
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                _connectionStatus.value = false
                dataProvider.stopSimulation()
            }
        })
    }

    override fun disconnect() {
        webSocket?.close(1000, "User disconnected")
        webSocket = null
        dataProvider.stopSimulation()
        _connectionStatus.value = false
    }

    private fun handleMessage(text: String) {
        try {
            val dto = jsonParser.decodeFromString<StockDto>(text)

            _stocks.update { currentList ->
                currentList.map { domainStock ->
                    if (domainStock.symbol == dto.symbol) {
                        // MAPPING: We update the price AND ensure the name matches the DTO
                        domainStock.copy(
                            name = dto.name,
                            previousPrice = domainStock.price,
                            price = dto.price
                        )
                    } else {
                        domainStock
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}