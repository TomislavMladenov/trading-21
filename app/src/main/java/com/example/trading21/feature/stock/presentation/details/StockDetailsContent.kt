package com.example.trading21.feature.stock.presentation.details

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.trading21.R
import com.example.trading21.base.presentation.ui.atom.spacer.DefaultSpacing
import com.example.trading21.base.presentation.ui.atom.spacer.FillAvailableHeightSpacer
import com.example.trading21.base.presentation.ui.atom.spacer.HugeSpacing
import com.example.trading21.base.presentation.ui.atom.spacer.SmallSpacing
import com.example.trading21.base.presentation.ui.components.card.Description
import com.example.trading21.base.presentation.ui.theme.PriceDown
import com.example.trading21.base.presentation.ui.theme.PriceUp
import com.example.trading21.base.presentation.ui.theme.T21Theme
import com.example.trading21.feature.stock.presentation.details.mvi.SelectedStock
import com.example.trading21.feature.stock.presentation.details.mvi.StockDetailsAction
import com.example.trading21.feature.stock.presentation.details.mvi.StockDetailsState

@Composable
fun StockDetailsContent(
    state: StockDetailsState,
    action: (StockDetailsAction) -> Unit,
) {
    if (state.selectedStock is SelectedStock.Available) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(T21Theme.dimens.horizontalPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = state.selectedStock.stock.symbol,
                style = MaterialTheme.typography.displayMedium,
                fontWeight = FontWeight.Bold
            )

            DefaultSpacing()

            val priceColor by animateColorAsState(
                targetValue = if (state.selectedStock.stock.isRising) PriceUp else PriceDown,
                label = "priceColor"
            )

            Text(
                text = "$${String.format("%.2f", state.selectedStock.stock.price)}",
                style = MaterialTheme.typography.displayLarge,
                color = priceColor,
                fontWeight = FontWeight.Bold
            )

            HugeSpacing()

            Description(description = state.selectedStock.stock.description)
        }
    } else {
        StockNotFoundScreen { action(StockDetailsAction.OnBack) }
    }
}

@Composable
fun StockNotFoundScreen(
    onBackClick: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        HugeSpacing()

        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "Not Found",
            modifier = Modifier.size(64.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 2. Clear Headline
        Text(
            text = stringResource(R.string.tv_stock_not_found),
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onSurface
        )

        SmallSpacing()

        // 3. Helpful Subtitle
        Text(
            text = stringResource(R.string.tv_stock_not_found_message),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        FillAvailableHeightSpacer()

        // 4. Action Button
        Button(
            onClick = onBackClick
        ) {
            Text(text = "Go Back")
        }

        HugeSpacing()
    }
}