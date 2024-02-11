package com.lnavamuelp.historicalroots

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.lnavamuelp.historicalroots.ui.screens.Drawer
import com.lnavamuelp.historicalroots.ui.screens.NavigationRoutes
import com.lnavamuelp.historicalroots.ui.screens.authenticatedGraph
import com.lnavamuelp.historicalroots.ui.screens.unauthenticatedGraph
import com.lnavamuelp.historicalroots.ui.theme.HistoricalRootsTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity: ComponentActivity() {

 //   private val historicalPlacesListViewModel: HistoricalPlacesListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HistoricalRootsTheme {
                MainApp()
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainApp() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        val navController = rememberNavController()
        //MainAppNavHost(navController = navController,historicalPlacesListViewModel = historicalPlacesListViewModel)
        MainAppNavHost(navController = navController)

    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainAppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    var drawerState = rememberDrawerState(DrawerValue.Closed)
    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = drawerState.isOpen,
        drawerContent = {
            Drawer { route ->
                navController.navigate(route)
            }
        }
    ) {
        Scaffold(
            modifier = modifier,
            topBar = {
                // You can add a top app bar here if needed
            }
        ) {
            NavHost(
                navController = navController,
                startDestination = NavigationRoutes.Unauthenticated.NavigationRoute.route,
            ) {
                // Unauthenticated user flow screens
                unauthenticatedGraph(navController = navController)
                // Authenticated user flow screens
                authenticatedGraph(navController = navController)
            }
        }
    }
}