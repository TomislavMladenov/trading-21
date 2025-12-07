package com.example.trading21.feature.stock.presentation.list

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.trading21.feature.stock.domain.model.Stock
import com.example.trading21.feature.stock.presentation.list.mvi.StockListAction
import com.example.trading21.feature.stock.presentation.list.mvi.StockListState

@Composable
fun StockListContent(
    state: StockListState,
    action: (StockListAction) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(state.stocks, key = { it.symbol }) { stock ->
            StockRow(stock) {
                action(StockListAction.OnSelect(stock))
            }
        }
    }
}

@Composable
fun StockRow(stock: Stock, onClick: () -> Unit) {
    // Simple flashing logic via text color
    val priceColor by animateColorAsState(
        targetValue = if (stock.isRising) Color(0xFF4CAF50) else Color(0xFFE53935),
        label = "priceColor"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(text = stock.symbol, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text(text = stock.name, style = MaterialTheme.typography.bodySmall)
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = if (stock.isRising) "↑" else "↓",
                    color = priceColor,
                    modifier = Modifier.padding(end = 4.dp)
                )
                Text(
                    text = "$${String.format("%.2f", stock.price)}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = priceColor
                )
            }
        }
    }
}