package com.example.trading21.base.presentation.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.trading21.base.presentation.util.Constants
import kotlin.collections.forEach
import kotlin.collections.isNotEmpty

sealed class NavDestination(
    open val destination: String,
    open val args: Map<String, String> = emptyMap()
) {
    data class StockDetails(
        val vehicleId: String = ""
    ) : NavDestination(
        destination = "stockDetails",
        args = mapOf(
            Pair(Constants.NavArgs.STOCK_UUID, vehicleId)
        )
    )

    data object StockListView : NavDestination("stockList")

    val arguments: List<NamedNavArgument> = if (args.isNotEmpty()) {
        buildList {
            args.forEach { element ->
                add(navArgument(element.key) {
                    type = NavType.StringType
                })
            }
        }
    } else emptyList()

    val route: String
        get() = buildString {
            append(destination)
            args.forEach { argument ->
                append("/${argument.key}/{${argument.key}}")
            }
        }

    val routePath: String
        get() = buildString {
            append(destination)
            args.forEach { argument ->
                append("/${argument.key}/${argument.value}")
            }
        }
}
