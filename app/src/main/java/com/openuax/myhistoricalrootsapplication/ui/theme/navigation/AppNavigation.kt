package com.openuax.myhistoricalrootsapplication.ui.theme.navigation

enum class Screen {
    HOME,
    LOGIN,
}
sealed class NavigationItem(val route: String) {
    object Home : NavigationItem(Screen.HOME.name)
    object Login : NavigationItem(Screen.LOGIN.name)
}