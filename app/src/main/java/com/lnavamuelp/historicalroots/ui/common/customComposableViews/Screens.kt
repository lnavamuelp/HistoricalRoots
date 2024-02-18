package com.lnavamuelp.historicalroots.ui.common.customComposableViews

sealed class Screen(val route: String) {
    object About : Screen("AboutUs")
    object Contact : Screen("Contact")
    object Settings : Screen("Settings")

}
