package com.example.trading21.feature.stockdetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.trading21.feature.stockdetails.mvi.StockDetailsAction
import com.example.trading21.feature.stockdetails.mvi.StockDetailsState

@Composable
fun StockDetailsContent(
    state: StockDetailsState,
    action: (StockDetailsAction) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Empty screen")
    }
}