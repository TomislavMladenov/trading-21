package com.example.trading21.feature.stock.presentation.details

import com.example.trading21.base.presentation.viewmodel.BaseViewModel
import com.example.trading21.feature.stock.presentation.details.mvi.StockDetailsAction
import com.example.trading21.feature.stock.presentation.details.mvi.StockDetailsState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StockDetailsViewModel
@Inject constructor() : BaseViewModel<StockDetailsState, StockDetailsAction>(StockDetailsState()) {

    override suspend fun handleActions(action: StockDetailsAction) {
        when (action) {
            is StockDetailsAction.OnBack -> {
                // TODO
            }
        }
    }
}