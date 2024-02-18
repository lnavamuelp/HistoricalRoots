package com.lnavamuelp.historicalroots.ui.screens.about

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavController

@Composable
fun AlertDialog(navController: NavController) {
    MaterialTheme {
        Column {
            val openDialog = remember { mutableStateOf(false)  }

            LaunchedEffect(Unit) {
                openDialog.value = true
            }

            // Se muestra el cuadro de diálogo solo si openDialog es true
            if (openDialog.value) {
                AlertDialog(
                    onDismissRequest = {
                        openDialog.value = false
                        navController.popBackStack()
                    },
                    title = {
                        Text(text = "App Information")
                    },
                    text = {
                        Text("This is an Android App developed by Laro Navamuel with JetPackComoose and Hilt + Kotlin.")
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                openDialog.value = false
                                navController.popBackStack()
                            }
                        ) {
                            Text("OK")
                        }
                    }
                )
            }
        }
    }
}
