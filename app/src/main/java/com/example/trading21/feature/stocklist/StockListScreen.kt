package com.example.trading21.feature.stocklist

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun StockListScreen() {

    val viewModel = hiltViewModel<StockListViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()

    StockListContent(state) {
        viewModel.submitAction(it)
    }
}