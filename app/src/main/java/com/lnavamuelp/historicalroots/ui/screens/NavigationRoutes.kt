package com.lnavamuelp.historicalroots.ui.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Login
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AppRegistration
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.TempleBuddhist
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class NavigationRoutes {

    // Unauthenticated Routes
    sealed class Unauthenticated(val title: String, val route: String, var icon: ImageVector) : NavigationRoutes() {
        object NavigationRoute : Unauthenticated("Home", "unauthenticated", Icons.Default.Home)
        object Login : Unauthenticated("Login", "login", Icons.AutoMirrored.Filled.Login)
        object Registration : Unauthenticated("Registration", "registration", Icons.Default.AppRegistration)
    }

    // Authenticated Routes
    sealed class Authenticated(val title: String, val route: String, var icon: ImageVector, val arguments: List<NamedNavArgument> = emptyList()) : NavigationRoutes() {
        object NavigationRoute : Authenticated("Authenticated", "authenticated", Icons.Default.VerifiedUser)
        object Dashboard : Authenticated("Dashboard", "Dashboard", Icons.Default.VerifiedUser)
        object HistoricalPlacesList : Authenticated("Historical Places List", "HistoricalPlacesList", Icons.Default.TempleBuddhist)
        object AboutUs : Authenticated("About Us", "AboutUs", Icons.Default.People)
        object Contact : Authenticated("Contact Us", "Contact", Icons.Default.Mail)
        object Settings : Authenticated("Settings", "Settings", Icons.Default.Settings)

        object AddHistoricalPlaces : Authenticated(
            "Add / Edit Places",
            "AddHistoricalPlaces",
            Icons.Default.Add,
            listOf(navArgument("placeId") { type = NavType.StringType },
                navArgument("isEdit") { type = NavType.BoolType })
        )
        object HistoricalPlaceDetail : Authenticated(
            "Historical Place Detail",
            "ViewHistoricalPlaceDetail",
            Icons.Default.RemoveRedEye,
            listOf(navArgument("placeId") { type = NavType.StringType })
        )

    }

}

