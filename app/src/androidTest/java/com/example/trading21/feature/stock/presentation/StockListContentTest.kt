package com.example.trading21.feature.stock.presentation

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.trading21.base.presentation.ui.theme.Trading21Theme
import com.example.trading21.feature.stock.domain.model.Stock
import com.example.trading21.feature.stock.presentation.list.StockListContent
import com.example.trading21.feature.stock.presentation.list.mvi.StockListAction
import com.example.trading21.feature.stock.presentation.list.mvi.StockListState
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class StockListContentTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val sampleStocks = listOf(
        Stock(
            symbol = "TSLA",
            name = "Tesla",
            price = 900.0,
            previousPrice = 850.0 // Price up
        ),
        Stock(
            symbol = "AAPL",
            name = "Apple",
            price = 150.50,
            previousPrice = 160.0 // Price down
        )
    )

    @Test
    fun stockList_displaysStocks_withCorrectFormatting() {
        // GIVEN
        val state = StockListState(stocks = sampleStocks)

        // WHEN
        composeTestRule.setContent {
            Trading21Theme {
                StockListContent(state = state, action = {})
            }
        }

        // THEN
        // 1. Check TSLA symbol exists
        composeTestRule.onNodeWithText("TSLA").assertIsDisplayed()

        // 2. Check TSLA formatted price ($900.00)
        composeTestRule.onNodeWithText("$900.00").assertIsDisplayed()

        // 3. Check Arrow for Rising stock (TSLA is rising)
        composeTestRule.onNodeWithText("↑").assertIsDisplayed()

        // 4. Check AAPL symbol exists
        composeTestRule.onNodeWithText("AAPL").assertIsDisplayed()
    }

    @Test
    fun stockList_onClick_triggersOnSelectAction() {
        // GIVEN
        val state = StockListState(stocks = sampleStocks)
        val capturedActions = mutableListOf<StockListAction>()

        composeTestRule.setContent {
            Trading21Theme {
                StockListContent(
                    state = state,
                    action = { action -> capturedActions.add(action) }
                )
            }
        }

        // WHEN - Click on the text "TSLA"
        composeTestRule.onNodeWithText("TSLA").performClick()

        // THEN
        assertEquals(1, capturedActions.size)
        val action = capturedActions.first()

        assertTrue(action is StockListAction.OnSelect)
        assertEquals("TSLA", (action as StockListAction.OnSelect).stock.symbol)
    }
}