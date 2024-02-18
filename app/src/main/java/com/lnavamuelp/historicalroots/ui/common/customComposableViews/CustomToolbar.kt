package com.lnavamuelp.historicalroots.ui.common.customComposableViews

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.lnavamuelp.historicalroots.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomToolbarWithBackArrow(title: String, navController: NavController) {
    TopAppBar(
        title = { Text(text = title, style = MaterialTheme.typography.headlineLarge) },
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(id = R.string.back),
                    tint = Color.Black
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomToolbar(title: String,openDrawer: () -> Unit) {
    TopAppBar(
        title = { Text(text = title, style = MaterialTheme.typography.headlineLarge) },
        navigationIcon = {
            IconButton(onClick = { openDrawer() } ) {
                Icon(Icons.Default.Menu, contentDescription = "navigation drawer")
            }
        },
    )
}