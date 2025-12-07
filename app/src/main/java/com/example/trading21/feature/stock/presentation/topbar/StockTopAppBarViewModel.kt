package com.example.trading21.feature.stock.presentation.topbar

import androidx.lifecycle.viewModelScope
import com.example.trading21.base.presentation.navigation.NavigationCommand
import com.example.trading21.base.presentation.navigation.NavigationManager
import com.example.trading21.base.presentation.viewmodel.BaseViewModel
import com.example.trading21.feature.stock.domain.interactor.ObserveStockConnection
import com.example.trading21.feature.stock.domain.interactor.ToggleStockUpdates
import com.example.trading21.feature.stock.presentation.topbar.mvi.StockTopAppBarAction
import com.example.trading21.feature.stock.presentation.topbar.mvi.StockTopAppBarState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class StockTopAppBarViewModel
@Inject
constructor(
    private val observeStockConnection: ObserveStockConnection,
    private val toggleStockUpdates: ToggleStockUpdates,
    private val navigationManager: NavigationManager
) : BaseViewModel<StockTopAppBarState, StockTopAppBarAction>(
    StockTopAppBarState()
) {

    init {
        subscribeObservers()
    }

    private fun subscribeObservers() {
        observeStockConnection().onEach { isConnected ->
            updateState { copy(isConnected = isConnected) }
        }.launchIn(viewModelScope)
    }

    private fun toggle() = toggleStockUpdates.invoke()

    private suspend fun back() {
        navigationManager.navigate(NavigationCommand.Back)
    }

    override suspend fun handleActions(action: StockTopAppBarAction) {
        when (action) {
            StockTopAppBarAction.ToggleConnection -> toggle()
            StockTopAppBarAction.OnBack -> back()
        }
    }
}