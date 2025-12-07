package com.example.trading21.feature.stockdetails

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun StockDetailsScreen() {

    val viewModel = hiltViewModel<StockDetailsViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()

    StockDetailsContent(state) {
        viewModel.submitAction(it)
    }
}