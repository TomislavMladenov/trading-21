package com.example.trading21.feature.stock.domain.interactor

import app.cash.turbine.test
import com.example.trading21.base.core.util.StandardTestDispatcherProvider
import com.example.trading21.feature.stock.datasource.network.FakeTestStockApiDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.currentTime
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ObserveStockListTest {

    @Test
    fun `should stop emitting updates after disconnect`() = runTest {
        val dispatcherProvider = StandardTestDispatcherProvider(testScheduler)
        val dataSource = FakeTestStockApiDataSource(dispatcherProvider)
        val observeStockList = ObserveStockList(dataSource)

        observeStockList().test {
            skipItems(1) // Skip initial
            dataSource.connect()

            // Ensure we are receiving updates initially
            awaitItem()

            // ACTION: Disconnect
            dataSource.disconnect()

            // VERIFICATION:
            // We advance time beyond the 2000ms interval.
            // If the loop wasn't cancelled, this would trigger a new emission and fail the test.
            advanceTimeBy(5000)

            expectNoEvents()
        }
    }

    @Test
    fun `connect updates stocks with at least 2 seconds delay between emissions`() = runTest {
        val dispatcherProvider = StandardTestDispatcherProvider(testScheduler)

        val dataSource = FakeTestStockApiDataSource(dispatcherProvider)
        val observeStockList = ObserveStockList(dataSource)

        observeStockList().test {
            // Skip the initial empty/default state emitted upon creation
            skipItems(1)

            // 3. Trigger connection
            dataSource.connect()

            // 4. Verify first immediate update
            awaitItem()
            val startTime = currentTime

            // 5. Wait for the NEXT emission (the loop update)
            // Turbine will auto-advance the virtual clock until an item is found
            awaitItem()

            val endTime = currentTime
            val duration = endTime - startTime

            // 6. Assert that time passed is >= 2000ms
            assertTrue(
                "Emissions should be at least 2s apart. Actual: $duration ms",
                duration >= 2000
            )

            // Cleanup
            dataSource.disconnect()
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `should resume emissions upon reconnection`() = runTest {
        val dispatcherProvider = StandardTestDispatcherProvider(testScheduler)
        val dataSource = FakeTestStockApiDataSource(dispatcherProvider)
        val observeStockList = ObserveStockList(dataSource)

        observeStockList().test {
            skipItems(1)

            // 1. First Session
            dataSource.connect()
            awaitItem() // Received update
            dataSource.disconnect()

            // Verify silence
            advanceTimeBy(3000)
            expectNoEvents()

            // 2. Second Session (Re-connect)
            dataSource.connect()

            // Should receive a new item immediately (or after delay, depending on your logic)
            // Turbine waits for it, proving the Job was recreated
            awaitItem()

            dataSource.disconnect()
            cancelAndIgnoreRemainingEvents()
        }
    }


}