package com.lnavamuelp.historicalroots.ui.screens.historicalplaces

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.lnavamuelp.historicalroots.R
import com.lnavamuelp.historicalroots.database.HistoricPlace
import com.lnavamuelp.historicalroots.ui.common.customComposableViews.CustomToolbarWithBackArrow
import com.lnavamuelp.historicalroots.ui.screens.NavigationRoutes

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalMaterial3Api
@Composable
fun HistoricalPlaceDetailScreen(
    navController: NavHostController,
    historicalPlacesListViewModel: HistoricalPlacesListViewModel,
    placeId: String?
) {

    historicalPlacesListViewModel.findPlaceById(placeId!!)
    val selectedPlace = historicalPlacesListViewModel.foundPlace.observeAsState().value
    val showDialog = remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CustomToolbarWithBackArrow(title = "Place Details", navController = navController)
        }
    ) {
        if (selectedPlace != null) {
            Surface(color = Color.White, modifier = Modifier.fillMaxSize()) {
                Card(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_launcher_foreground),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            colorFilter = ColorFilter.tint(
                                colorResource(id = R.color.purple_200),
                            ),
                            modifier = Modifier
                                .size(140.dp)
                                .clip(RoundedCornerShape(50)),
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "#${selectedPlace.placeId} - ${selectedPlace.name}",
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "(${selectedPlace.category})",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold,
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "${selectedPlace.short_description} ",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "${selectedPlace.long_description} ",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "${selectedPlace.latitude} | ${selectedPlace.longitude}",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Row() {
                            if (showDialog.value) {
                                Alert(navController,
                                    historicalPlacesListViewModel,
                                    selectedPlace,
                                    name = "Are you sure you want to delete the employee?",
                                    showDialog = showDialog.value,
                                    onDismiss = { showDialog.value = false })
                            }
                            Button(
                                onClick = {
                                    showDialog.value = true

                                },
                                modifier = Modifier
                                    .weight(1f)
                            ) {
                                Text(
                                    text = "Delete Employee",
                                    fontSize = 16.sp,
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Button(
                                onClick = {
                                    navController.navigate(NavigationRoutes.Authenticated.AddHistoricalPlaces.route + "/" + selectedPlace.placeId + "/" + true)
                                },
                                modifier = Modifier
                                    .weight(1f)
                            ) {
                                Text(text = "Update Details", fontSize = 16.sp)
                            }
                        }
                    }
                }
            }
        }

    }
}

@Composable
fun Alert(
    navController: NavHostController,
    historicalPlacesListViewModel: HistoricalPlacesListViewModel,
    selectedPlace: HistoricPlace,
    name: String,
    showDialog: Boolean,
    onDismiss: () -> Unit
) {
    if (showDialog) {
        AlertDialog(
            title = {
                Text("Delete Employee", style = MaterialTheme.typography.bodySmall)
            },
            text = {
                Text(text = name)
            },
            onDismissRequest = onDismiss,
            confirmButton = {
                TextButton(onClick = {
                    historicalPlacesListViewModel.deletePlaceDetails(selectedPlace)
                    navController.popBackStack()
                }) {
                    Text("DELETE")
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text("CANCEL")
                }
            }
        )
    }
}
