package com.lnavamuelp.historicalroots.ui.common.customComposableViews

import android.app.Activity
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode

@Composable
fun AutocompletePlace() {
    val context = LocalContext.current

    val intentLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) {
        when (it.resultCode) {
            Activity.RESULT_OK -> {
                it.data?.let {
                    val place = Autocomplete.getPlaceFromIntent(it)
                    Log.i("MAP_ACTIVITY", "Place: ${place.name}, ${place.id}")
                }
            }
            AutocompleteActivity.RESULT_ERROR -> {
                it.data?.let {
                    val status = Autocomplete.getStatusFromIntent(it)
                    Log.i("MAP_ACTIVITY", status.statusMessage!!)

                }
            }
            Activity.RESULT_CANCELED -> {
                // The user canceled the operation.
            }
        }
    }

    val launchMapInputOverlay = {
        Places.initialize(context, "MAPS_API_KEY")
        val fields = listOf(Place.Field.ID, Place.Field.NAME)
        val intent = Autocomplete
            .IntentBuilder(AutocompleteActivityMode.OVERLAY, fields)
            .build(context)
        intentLauncher.launch(intent)
    }

    Column {
        Button(onClick = launchMapInputOverlay) {
            Text("Select Location")
        }
    }
}