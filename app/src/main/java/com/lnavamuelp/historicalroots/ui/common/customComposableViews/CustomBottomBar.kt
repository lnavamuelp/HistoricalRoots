package com.lnavamuelp.historicalroots.ui.common.customComposableViews

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Contacts
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController


@Composable
fun BottomAppBarWithNavigation(navController: NavController) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color.White,
        shape = CircleShape
    ) {
        BottomAppBar(
            contentColor = Color.Black // Color de los iconos de la barra inferior
        ) {
            NavigationItem(navController, Screen.About, Icons.Default.AccountBox, "About Us")
            NavigationItem(navController, Screen.Contact, Icons.Default.Contacts, "Contact Us")
            NavigationItem(navController, Screen.Settings , Icons.Default.Settings, "Settings")
        }
    }
}

@Composable
fun NavigationItem(
    navController: NavController,
    screen: Screen,
    icon: ImageVector,
    label: String
) {
    IconButton(
        onClick = { navController.navigate(screen.route) }
    ) {
        Icon(imageVector = icon, contentDescription = label)
    }
}
