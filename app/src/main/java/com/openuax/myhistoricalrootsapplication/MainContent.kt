package com.openuax.myhistoricalrootsapplication

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.openuax.myhistoricalrootsapplication.interfaces.LoginForm
import com.openuax.myhistoricalrootsapplication.ui.theme.ARG_MOVIE_ID
import com.openuax.myhistoricalrootsapplication.ui.theme.Screen

val LocalNavController = compositionLocalOf<NavHostController> { error("No nav controller") }
val LocalDarkTheme = compositionLocalOf { mutableStateOf(false) }

@Composable
fun MainContent() {
    val navController = LocalNavController.current
    NavHost(navController = navController, startDestination = Screen.MOVIES.route) {
        composable(Screen.MOVIES.route) { LoginForm() }

        navigation(startDestination = Screen.DETAIL.route, route = "movie") {
            navArgument(ARG_MOVIE_ID) { type = NavType.StringType }

            fun NavBackStackEntry.movieId(): Int {
                return arguments?.getString(ARG_MOVIE_ID)!!.toInt()
            }

            //val movieDetailViewModel: @Composable (movieId: Int) -> MovieDetailViewModel = { hiltViewModel() }
        }
    }
}