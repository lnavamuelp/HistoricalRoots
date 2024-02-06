package com.lnavamuelp.historicalroots.ui.screens

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.lnavamuelp.historicalroots.ui.screens.historicalplaces.AddEditHistoricalPlaceScreen
import com.lnavamuelp.historicalroots.ui.screens.historicalplaces.HistoricalPlacesList
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
fun NavGraphBuilder.authenticatedGraph(navController: NavController) {
    navigation(
        route = NavigationRoutes.Authenticated.NavigationRoute.route,
        startDestination = NavigationRoutes.Authenticated.Dashboard.route
    ) {
        // Dashboard
        composable(route = NavigationRoutes.Authenticated.Dashboard.route) {
          //DashboardScreen()
          //AboutScreen()
            HistoricalPlacesList(navController =navController,openDrawer = {})
        }
        // Historical Places List
        composable(route = NavigationRoutes.Authenticated.HistoricalPlacesList.route) {
            //HistoricalPlacesList(navController = navController)
        }

        //Add Historical Places List
        composable(route = NavigationRoutes.Authenticated.AddHistoricalPlaces.route) {
                navBackStackEntry ->
            val placeToEdit = navBackStackEntry.arguments?.getString("placeToEdit") ?: "0"
            val isEdit = navBackStackEntry.arguments?.getBoolean("isEdit") ?: false
            AddEditHistoricalPlaceScreen(
                navController = navController,
                placeToEdit = placeToEdit,
                isEdit = isEdit
            )
        }
    }
}