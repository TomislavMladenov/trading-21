package com.example.trading21

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.trading21.base.presentation.navigation.NavDestination
import com.example.trading21.base.presentation.navigation.NavigationCommand
import com.example.trading21.base.presentation.navigation.NavigationManager
import com.example.trading21.base.presentation.ui.theme.Trading21Theme
import com.example.trading21.feature.stock.presentation.details.StockDetailsScreen
import com.example.trading21.feature.stock.presentation.list.StockListScreen
import com.example.trading21.feature.topbar.topbar.Trading21AppTopbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navigationManager: NavigationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Trading21Theme {
                RootComposable(navigationManager)
            }
        }
    }
}

@Composable
fun RootComposable(
    navigationManager: NavigationManager,
    navController: NavHostController = rememberNavController()
) {

    Scaffold(
        topBar = {
            Trading21AppTopbar(navController, navController::navigateUp)
        }
    ) { paddingValues ->
        NavHost(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            navController = navController,
            startDestination = NavDestination.StockListView.route
        ) {
            composable(
                route = NavDestination.StockDetails().route,
                arguments = NavDestination.StockDetails().arguments
            ) {
                StockDetailsScreen()
            }
            composable(route = NavDestination.StockListView.route) {
                StockListScreen()
            }
        }
    }

    SubscribeToNavEvents(navController, navigationManager)
}

@Composable
private fun SubscribeToNavEvents(
    navHostController: NavHostController,
    navigationManager: NavigationManager
) {
    val lifecycle = androidx.lifecycle.compose.LocalLifecycleOwner.current
    LaunchedEffect(Unit) {
        lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
            navigationManager.navActions.collectLatest { navigationCommand ->
                navigate(navigationCommand, navHostController)
            }
        }
    }
}

private fun navigate(navigationCommand: NavigationCommand, navHostController: NavHostController) {
    Timber.i("$navigationCommand")
    when (navigationCommand) {
        NavigationCommand.Back -> {
            Timber.i("on back triggered.")
            navHostController.navigateUp()
        }

        is NavigationCommand.Navigate -> {
            val currentDestination = navHostController.currentDestination?.route
            Timber.i("current = $currentDestination, new = ${navigationCommand.destination.route}")
            Timber.i("changed to $navigationCommand")
            // Handle in NavHost
            navHostController.navigate(
                route = navigationCommand.destination.routePath
            )
        }
    }
}