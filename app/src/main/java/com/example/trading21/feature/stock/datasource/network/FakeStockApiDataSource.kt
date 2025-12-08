package com.example.trading21.feature.stock.datasource.network

import com.example.trading21.base.core.util.DispatcherProvider
import com.example.trading21.feature.stock.datasource.network.model.StockDto
import com.example.trading21.feature.stock.datasource.network.model.fromDto
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
import timber.log.Timber

class FakeStockApiDataSource(
    dispatcher: DispatcherProvider,
) : StockApiDataSource {
    private val jsonParser: Json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        encodeDefaults = true
        explicitNulls = false
    }

    private val dataProvider: FakeDataProvider = FakeDataProvider(dispatcher, jsonParser)
    private val _stocks = MutableStateFlow(dataProvider.generateInitialStocks())
    override val stocks = _stocks.asStateFlow()

    private val _connectionStatus = MutableStateFlow(false)
    override val connectionStatus: StateFlow<Boolean> = _connectionStatus.asStateFlow()
    private val client = OkHttpClient()
    private var webSocket: WebSocket? = null

    override fun connect() {
        val request = Request.Builder().url("wss://ws.postman-echo.com/raw").build()
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
            Timber.d("New data: $text")
            val dto = jsonParser.decodeFromString<List<StockDto>>(text)
            _stocks.update { it.fromDto(dto) }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}