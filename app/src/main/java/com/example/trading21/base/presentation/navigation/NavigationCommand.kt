package com.example.trading21.base.presentation.navigation

sealed class NavigationCommand {
    data class Navigate(val destination: NavDestination): NavigationCommand()
    data object Back : NavigationCommand()
}