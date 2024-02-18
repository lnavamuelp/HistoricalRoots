package com.lnavamuelp.historicalroots.ui.screens.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.lnavamuelp.historicalroots.di.UserPreferences


@Composable
fun PreferencesScreen(navController: NavController) {
    val context = LocalContext.current
    var language by remember { mutableStateOf("en") }
    var themeColor by remember { mutableStateOf(0.5f) }
    var notificationsEnabled by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Selector de idioma
        DropdownMenu(
            modifier = Modifier.fillMaxWidth(),
            expanded = false, // Cambiar a true para mostrar el menú desplegable
            onDismissRequest = { /* No hacer nada */ }
        ) {
            DropdownItem(text = "English", onItemClick = {
                language = "en"
                UserPreferences.saveLanguage(context, language =language)
            })
            DropdownItem(text = "Spanish", onItemClick = {
                language = "es"
                UserPreferences.saveLanguage(context, language =language)
            })
        }

        // Control deslizante de color
        Slider(
            value = themeColor,
            onValueChange = { themeColor = it },
            modifier = Modifier.fillMaxWidth()
        )

        // Interruptor de notificaciones
        Switch(
            checked = notificationsEnabled,
            onCheckedChange = { notificationsEnabled = it }
        )

        // Botón para guardar preferencias
        Button(
            onClick = {
                // Guardar preferencias
                UserPreferences.saveLanguage(context, language)
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(text = "Save")
        }
    }
}


@Composable
fun DropdownItem(text: String, onItemClick: () -> Unit) {
    Text(
        text = text,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable(onClick = onItemClick)
    )
}