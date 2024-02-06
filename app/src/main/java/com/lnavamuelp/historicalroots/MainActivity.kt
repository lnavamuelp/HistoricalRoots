package com.lnavamuelp.historicalroots

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.lnavamuelp.historicalroots.ui.screens.NavigationRoutes
import com.lnavamuelp.historicalroots.ui.screens.authenticatedGraph
import com.lnavamuelp.historicalroots.ui.screens.historicalplaces.HistoricalPlacesListViewModel
import com.lnavamuelp.historicalroots.ui.screens.unauthenticatedGraph
import com.lnavamuelp.historicalroots.ui.theme.HistoricalRootsTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity: ComponentActivity() {

    private val historicalPlacesListViewModel: HistoricalPlacesListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HistoricalRootsTheme {
                MainApp()
            }
        }
    }
}


@Composable
fun MainApp() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        val navController = rememberNavController()
        MainAppNavHost(navController = navController)
    }
}

@Composable
fun MainAppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = NavigationRoutes.Unauthenticated.NavigationRoute.route
    ) {
        // Unauthenticated user flow screens
        unauthenticatedGraph(navController = navController)
        // Authenticated user flow screens
        authenticatedGraph(navController = navController)
    }
}
