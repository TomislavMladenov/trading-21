@file:OptIn(ExperimentalCoroutinesApi::class)

package com.example.trading21.base.core.util

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler

class StandardTestDispatcherProvider(testScheduler: TestCoroutineScheduler) : DispatcherProvider {
    private val dispatcher = StandardTestDispatcher(testScheduler)
    override fun main(): CoroutineDispatcher = dispatcher
    override fun default(): CoroutineDispatcher = dispatcher
    override fun io(): CoroutineDispatcher = dispatcher
    override fun unconfined(): CoroutineDispatcher = dispatcher
}