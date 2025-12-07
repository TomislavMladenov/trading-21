package com.example.trading21.feature.stock.presentation.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.trading21.base.presentation.navigation.NavigationCommand
import com.example.trading21.base.presentation.navigation.NavigationManager
import com.example.trading21.base.presentation.util.Constants
import com.example.trading21.base.presentation.viewmodel.BaseViewModel
import com.example.trading21.feature.stock.domain.interactor.ObserveStockList
import com.example.trading21.feature.stock.domain.model.Stock
import com.example.trading21.feature.stock.presentation.details.mvi.SelectedStock
import com.example.trading21.feature.stock.presentation.details.mvi.StockDetailsAction
import com.example.trading21.feature.stock.presentation.details.mvi.StockDetailsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class StockDetailsViewModel
@Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val observeStockList: ObserveStockList,
    private val navigationManager: NavigationManager
) : BaseViewModel<StockDetailsState, StockDetailsAction>(StockDetailsState()) {

    private val stockSymbol: String =
        checkNotNull(savedStateHandle[Constants.NavArgs.STOCK_SYMBOL])

    init {
        Timber.d("Stock symbol: $stockSymbol")
        if (stockSymbol != Constants.DefValue.NO_SYMBOL) {
            viewModelScope.launch { subscribeObservers(stockSymbol) }
        }
    }

    private fun subscribeObservers(symbol: String) {
        observeStockList().onEach { stocks ->
            getStockDetails(symbol, stocks)
        }.launchIn(viewModelScope)
    }

    private fun getStockDetails(symbol: String, stocks: List<Stock>) {
        stocks.firstOrNull { it.symbol == symbol }?.let {
            updateState { copy(selectedStock = SelectedStock.Available(it)) }
        } ?: updateState { copy(selectedStock = SelectedStock.NotFound) }
    }

    override suspend fun handleActions(action: StockDetailsAction) {
        when (action) {
            StockDetailsAction.OnBack -> navigationManager.navigate(NavigationCommand.Back)
        }
    }
}