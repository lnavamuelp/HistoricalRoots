package com.lnavamuelp.historicalroots.ui.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Login
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AppRegistration
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.TempleBuddhist
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavigationRoutes {

    // Unauthenticated Routes
    sealed class Unauthenticated(val title: String, val route: String, var icon: ImageVector) : NavigationRoutes() {
        object NavigationRoute : Unauthenticated("Home", "unauthenticated", Icons.Default.Home)
        object Login : Unauthenticated("Login", "login", Icons.AutoMirrored.Filled.Login)
        object Registration : Unauthenticated("Registration", "registration", Icons.Default.AppRegistration)
    }

    // Authenticated Routes
    sealed class Authenticated(val title: String,val route: String,var icon: ImageVector) : NavigationRoutes() {
        object NavigationRoute : Authenticated("Authenticated","authenticated",Icons.Default.VerifiedUser)
        object Dashboard : Authenticated("Dashboard","Dashboard",Icons.Default.VerifiedUser)
        object HistoricalPlacesList : Authenticated("Historical Places List", "HistoricalPlacesList", Icons.Default.TempleBuddhist)
        object AddHistoricalPlaces : Authenticated(
            "Add / Edit Places",
            "AddHistoricalPlaces",
            Icons.Default.Add
        ) {
            const val PLACE_ID = "placeId"
            const val IS_EDIT = "isEdit"
        }
        object AboutUs : Authenticated("About Us", "AboutUs", Icons.Default.People)
    }
}

