@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.trading21.feature.topbar.topbar

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.trading21.base.presentation.navigation.stockDestinations
import com.example.trading21.base.presentation.ui.atom.text.TopbarTitle
import com.example.trading21.feature.stock.presentation.topbar.StockAppTopbar

@Composable
fun Trading21AppTopbar(
    navHostController: NavHostController,
    onBack: () -> Unit
) {
    val navBackStackEntry by navHostController.currentBackStackEntryAsState()

    if (isInStockFeature(navBackStackEntry)) {
        StockAppTopbar(navBackStackEntry, onBack)
    } else {
        DefaultAppTopbar()
    }
}

/**
 * Demonstration purpose only in a real case scenario we would have many features
 */
@Composable
private fun DefaultAppTopbar() {
    TopAppBar(
        title = { TopbarTitle() },
        colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.primaryContainer),
    )
}

@Composable
fun isInStockFeature(navBackStackEntry: NavBackStackEntry?): Boolean {
    val currentDest = navBackStackEntry?.destination?.route ?: ""
    return currentDest in stockDestinations
}