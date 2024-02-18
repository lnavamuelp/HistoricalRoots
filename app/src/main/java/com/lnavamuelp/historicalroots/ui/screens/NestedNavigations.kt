package com.lnavamuelp.historicalroots.ui.screens

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.lnavamuelp.historicalroots.ui.screens.about.AlertDialog
import com.lnavamuelp.historicalroots.ui.screens.contact.ContactUsScreen
import com.lnavamuelp.historicalroots.ui.screens.historicalplaces.AddEditHistoricalPlaceScreen
import com.lnavamuelp.historicalroots.ui.screens.historicalplaces.HistoricalPlacesList
import com.lnavamuelp.historicalroots.ui.screens.historicalplaces.ViewHistoricalPlaceDetail
import com.lnavamuelp.historicalroots.ui.screens.settings.PreferencesScreen
import com.lnavamuelp.historicalroots.ui.screens.unauthenticated.login.LoginScreen
import com.lnavamuelp.historicalroots.ui.screens.unauthenticated.registration.RegistrationScreen

fun NavGraphBuilder.unauthenticatedGraph(navController: NavController) {

    navigation(
        route = NavigationRoutes.Unauthenticated.NavigationRoute.route,
        startDestination = NavigationRoutes.Unauthenticated.Login.route
    ) {

        // Login
        composable(route = NavigationRoutes.Unauthenticated.Login.route) {
            LoginScreen(
                onNavigateToRegistration = {
                    navController.navigate(route = NavigationRoutes.Unauthenticated.Registration.route)
                },
                onNavigateToForgotPassword = {},
                onNavigateToAuthenticatedRoute = {
                    navController.navigate(route = NavigationRoutes.Authenticated.NavigationRoute.route) {
                        popUpTo(route = NavigationRoutes.Unauthenticated.NavigationRoute.route) {
                            inclusive = true
                        }
                    }
                },
            )
        }

        // Registration
        composable(route = NavigationRoutes.Unauthenticated.Registration.route) {
            RegistrationScreen(
                onNavigateBack = {
                    navController.navigateUp()
                },
                onNavigateToAuthenticatedRoute = {
                    navController.navigate(route = NavigationRoutes.Authenticated.NavigationRoute.route) {
                        popUpTo(route = NavigationRoutes.Unauthenticated.NavigationRoute.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}

/**
 * Authenticated screens nav graph builder
 */
@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.authenticatedGraph(
    navController: NavController
) {
    navigation(
        route = NavigationRoutes.Authenticated.NavigationRoute.route,
        startDestination = NavigationRoutes.Authenticated.Dashboard.route
    ) {
        // Dashboard
        composable(route = NavigationRoutes.Authenticated.Dashboard.route) {
            HistoricalPlacesList(navController =navController,openDrawer = {})
        }
        // Historical Places List
        composable(route = NavigationRoutes.Authenticated.HistoricalPlacesList.route) {
            //HistoricalPlacesList(navController = navController)
        }

        //Add Historical Places List
        composable(
            route = "${NavigationRoutes.Authenticated.AddHistoricalPlaces.route}/{placeId}/{isEdit}",
            arguments = listOf(
                navArgument("placeId") { type = NavType.StringType },
                navArgument("isEdit") { type = NavType.BoolType }
            )
        ) { navBackStackEntry ->
            val placeId = navBackStackEntry.arguments?.getString("placeId") ?: "0"
            val isEdit = navBackStackEntry.arguments?.getBoolean("isEdit") ?: false
            // Check if placeId and isEdit are provided
            AddEditHistoricalPlaceScreen(
                navController = navController,
                placeId = placeId,
                isEdit = isEdit
            )
        }


        //Historical Place Detail View
        composable(
            route = "${NavigationRoutes.Authenticated.HistoricalPlaceDetail.route}/{placeId}",
            arguments = listOf(navArgument("placeId") { type = NavType.StringType })
        ) { navBackStackEntry ->
            val placeId = navBackStackEntry.arguments?.getString("placeId") ?: ""
            if (placeId.isNotEmpty()) {
                ViewHistoricalPlaceDetail(navController = navController, placeId = placeId)
            } else {
                // Handle case where placeId is not provided
                navController.navigateUp()
            }
        }


        //Contact Us
        composable(route = NavigationRoutes.Authenticated.Contact.route) {
            ContactUsScreen(navController =navController)
        }

        //About Us
        composable(route = NavigationRoutes.Authenticated.AboutUs.route) {
            AlertDialog(navController =navController)
            //AboutUsScreen(navController =navController)
        }

        //Settings
        composable(route = NavigationRoutes.Authenticated.Settings.route) {
            PreferencesScreen(navController =navController)
        }


    }
}

