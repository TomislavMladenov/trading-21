package com.example.trading21.feature.stocklist

import com.example.trading21.base.presentation.viewmodel.BaseViewModel
import com.example.trading21.feature.stocklist.mvi.StockListAction
import com.example.trading21.feature.stocklist.mvi.StockListState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StockListViewModel
@Inject constructor(
) : BaseViewModel<StockListState, StockListAction>(StockListState()) {

    override suspend fun handleActions(action: StockListAction) {
        when (action) {
            is StockListAction.OnBack -> {
                // TODO
            }
        }

    }
}