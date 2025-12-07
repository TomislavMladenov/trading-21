package com.example.trading21.feature.stockdetails

import com.example.trading21.base.presentation.viewmodel.BaseViewModel
import com.example.trading21.feature.stockdetails.mvi.StockDetailsAction
import com.example.trading21.feature.stockdetails.mvi.StockDetailsState
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class StockDetailsViewModel : BaseViewModel<StockDetailsState, StockDetailsAction>(StockDetailsState()) {

    override suspend fun handleActions(action: StockDetailsAction) {
        when (action) {
            is StockDetailsAction.OnBack -> {
                // TODO
            }
        }
    }
}