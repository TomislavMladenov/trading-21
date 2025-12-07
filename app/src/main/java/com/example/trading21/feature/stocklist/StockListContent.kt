package com.example.trading21.feature.stocklist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.trading21.feature.stocklist.mvi.StockListAction
import com.example.trading21.feature.stocklist.mvi.StockListState

@Composable
fun StockListContent(
    state: StockListState,
    action: (StockListAction) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Empty screen")
    }
}