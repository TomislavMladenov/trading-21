package com.example.trading21.base.core.util

inline fun <reified T : Enum<T>> String.asEnumOrDefault(defaultValue: T): T =
    enumValues<T>().firstOrNull { it.name.equals(this, ignoreCase = true) } ?: defaultValue