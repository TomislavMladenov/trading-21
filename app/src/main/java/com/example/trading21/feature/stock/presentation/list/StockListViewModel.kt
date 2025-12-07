package com.example.trading21.feature.stock.presentation.list

import androidx.lifecycle.viewModelScope
import com.example.trading21.base.presentation.navigation.NavDestination
import com.example.trading21.base.presentation.navigation.NavigationCommand
import com.example.trading21.base.presentation.navigation.NavigationManager
import com.example.trading21.base.presentation.viewmodel.BaseViewModel
import com.example.trading21.feature.stock.domain.interactor.ObserveStockList
import com.example.trading21.feature.stock.domain.model.Stock
import com.example.trading21.feature.stock.presentation.list.mvi.StockListAction
import com.example.trading21.feature.stock.presentation.list.mvi.StockListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class StockListViewModel
@Inject constructor(
    private val observeStockList: ObserveStockList,
    private val navigationManager: NavigationManager
) : BaseViewModel<StockListState, StockListAction>(StockListState()) {

    init {
        subscribeObservers()
    }

    private fun subscribeObservers() {
        observeStockList().onEach { stocks ->
            updateState { copy(stocks = stocks) }
        }.launchIn(viewModelScope)
    }

    private suspend fun selectStock(stock: Stock) {
        navigationManager.navigate(
            NavigationCommand.Navigate(
                NavDestination.StockDetails(stock.symbol)
            )
        )
    }

    override suspend fun handleActions(action: StockListAction) {
        when (action) {
            is StockListAction.OnSelect -> selectStock(action.stock)
        }
    }
}