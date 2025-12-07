package com.example.trading21.base.core.util

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import timber.log.Timber

suspend fun <T> safeSuspendCall(block: suspend () -> T): Result<T> {
    return try {
        Result.success(block())
    } catch (ex: Exception) {
        Timber.e(ex)
        Result.failure(ex)
    }
}

fun <T> safeCall(block: () -> T): Result<T> {
    return try {
        Result.success(block())
    } catch (ex: Exception) {
        Timber.e(ex)
        Result.failure(ex)
    }
}

fun <T> Result<Flow<List<T>>>.unwrap(): Flow<List<T>> {
    return this.getOrElse {
        Timber.d("Unable to unwrap flow... ${this.javaClass::class.java.simpleName}")
        // Handle the error case, e.g., return an empty flow or throw an exception
        flowOf(emptyList()) // Or throw it
    }
}

fun <T> Result<T>.unwrap(): T? {
    return this.getOrElse {
        Timber.d("Unable to unwrap result... ${this.javaClass::class.java.simpleName}")
        null
    }
}