@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.trading21.feature.stock.presentation.topbar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import com.example.trading21.R
import com.example.trading21.base.presentation.navigation.NavDestination
import com.example.trading21.base.presentation.ui.atom.text.TopbarTitle
import com.example.trading21.base.presentation.ui.theme.Connected
import com.example.trading21.base.presentation.ui.theme.Disconnected
import com.example.trading21.base.presentation.ui.theme.T21Theme
import com.example.trading21.feature.stock.presentation.topbar.mvi.StockTopAppBarAction

@Composable
fun StockAppTopbar(
    navBackStackEntry: NavBackStackEntry?,
    onBack: () -> Unit
) {
    val viewModel = hiltViewModel<StockTopAppBarViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()

    TopAppBar(
        title = {
            Row(
                horizontalArrangement = Arrangement.spacedBy(T21Theme.dimens.space_1)
            ) {
                TopbarTitle()
                Text(
                    text = if (state.isConnected) "🟢" else "🔴",
                    fontSize = 18.sp
                )
            }
        },
        navigationIcon = {
            if (isDetails(navBackStackEntry)) {
                IconButton(onBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.action_back)
                    )
                }
            }
        },
        actions = {
            Button(
                modifier = Modifier.padding(end = T21Theme.dimens.space_1),
                onClick = { viewModel.submitAction(StockTopAppBarAction.ToggleConnection) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (state.isConnected) Disconnected else Connected
                )
            ) {
                Text(
                    if (state.isConnected) stringResource(R.string.tv_stop) else
                        stringResource(R.string.tv_start)
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.primaryContainer),
    )
}


@Composable
fun isDetails(navBackStackEntry: NavBackStackEntry?): Boolean {
    val currentDest = navBackStackEntry?.destination?.route ?: ""
    return currentDest == NavDestination.StockDetails().route
}