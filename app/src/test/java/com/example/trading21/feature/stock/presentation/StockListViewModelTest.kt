package com.example.trading21.feature.stock.presentation

import app.cash.turbine.test
import com.example.trading21.base.presentation.navigation.NavDestination
import com.example.trading21.base.presentation.navigation.NavigationCommand
import com.example.trading21.base.presentation.navigation.NavigationManager
import com.example.trading21.feature.stock.domain.interactor.ObserveStockList
import com.example.trading21.feature.stock.domain.model.Stock
import com.example.trading21.feature.stock.presentation.list.StockListViewModel
import com.example.trading21.feature.stock.presentation.list.mvi.StockListAction
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class StockListViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val observeStockList: ObserveStockList = mockk()

    private val navigationManager: NavigationManager = mockk(relaxed = true)

    private lateinit var viewModel: StockListViewModel

    @Test
    fun `should automatically subscribe and update state on init`() = runTest {
        // GIVEN
        val stocks = listOf(
            Stock(symbol = "AAPL", name = "Apple Inc.", price = 150.0),
            Stock(symbol = "GOOG", name = "Alphabet Inc.", price = 2800.0)
        )

        every { observeStockList() } returns flowOf(stocks)

        // WHEN
        viewModel = StockListViewModel(observeStockList, navigationManager)

        // THEN
        viewModel.state.test {
            val state = awaitItem()
            assertEquals(stocks, state.stocks)
        }
    }

    @Test
    fun `onSelect action should navigate to StockDetails`() = runTest {
        // GIVEN
        every { observeStockList() } returns flowOf(emptyList())
        viewModel = StockListViewModel(observeStockList, navigationManager)

        val selectedStock = Stock(symbol = "TSLA", name = "Tesla", price = 900.0)

        // WHEN
        viewModel.handleActions(StockListAction.OnSelect(selectedStock))

        // THEN
        coVerify {
            navigationManager.navigate(
                match { command ->
                    val navCommand = command as? NavigationCommand.Navigate
                    val destination = navCommand?.destination as? NavDestination.StockDetails
                    destination?.symbol == "TSLA"
                }
            )
        }
    }
}