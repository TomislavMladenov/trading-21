package com.example.trading21.feature.stock.presentation.list

import androidx.lifecycle.viewModelScope
import com.example.trading21.base.presentation.viewmodel.BaseViewModel
import com.example.trading21.feature.stock.domain.interactor.ObserveStockList
import com.example.trading21.feature.stock.presentation.list.mvi.StockListAction
import com.example.trading21.feature.stock.presentation.list.mvi.StockListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class StockListViewModel
@Inject constructor(
    private val observeStockList: ObserveStockList
) : BaseViewModel<StockListState, StockListAction>(StockListState()) {

    init {
        subscribeObservers()
    }

    private fun subscribeObservers() {
        observeStockList().onEach { stocks ->
            Timber.d("New stocks update: $stocks")
            updateState { copy(stocks = stocks) }
        }.launchIn(viewModelScope)
    }

    override suspend fun handleActions(action: StockListAction) {
        when (action) {
            is StockListAction.OnBack -> {
                // TODO
            }

            is StockListAction.OnSelect -> TODO()
        }

    }
}