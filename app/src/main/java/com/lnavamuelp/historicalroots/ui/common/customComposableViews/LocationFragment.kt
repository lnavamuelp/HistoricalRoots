package com.lnavamuelp.historicalroots.ui.common.customComposableViews/*import android.Manifest
import android.content.Context
import android.content.IntentSender
import android.location.Geocoder
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.icons.Icons
import androidx.compose.material3.icons.outlined.MyLocation
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.location.LocationManagerCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.libraries.places.api.Places
import com.lnavamuelp.historicalroots.BuildConfig
import com.lnavamuelp.historicalroots.ui.theme.HistoricalRootsTheme


class LocationFragment : Fragment() {

    private val viewModel by viewModels<LocationViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                HistoricalRootsTheme {
                    LocationScreen()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.fusedLocationClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())
        Places.initialize(requireContext().applicationContext, BuildConfig.MAPS_API_KEY)
        viewModel.placesClient = Places.createClient(requireContext())
        viewModel.geoCoder = Geocoder(requireContext())
    }

    @Composable
    fun LocationScreen(modifier: Modifier = Modifier) {
        val locationPermissionState by rememberMultiplePermissionsState(
            listOf(
                Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION
            )
        )

        LaunchedEffect(locationPermissionState.allPermissionsGranted) {
            if (locationPermissionState.allPermissionsGranted) {
                if (locationEnabled()) {
                    viewModel.getCurrentLocation()
                } else {
                    viewModel.locationState = LocationState.LocationDisabled
                }
            }
        }

        Column(
            modifier = modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (val state = viewModel.locationState) {
                is LocationState.NoPermission -> {
                    Text("We need location permission to continue")
                    Button(onClick = { locationPermissionState.launchMultiplePermissionRequest() }) {
                        Text("Request permission")
                    }
                }

                is LocationState.LocationDisabled -> {
                    Text("We need location to continue")
                    Button(onClick = { requestLocationEnable() }) {
                        Text("Enable location")
                    }
                }

                is LocationState.LocationLoading -> {
                    Text("Loading Map")
                }

                is LocationState.Error -> {
                    Text("Error fetching your location")
                    Button(onClick = { viewModel.getCurrentLocation() }) {
                        Text("Retry")
                    }
                }

                is LocationState.LocationAvailable -> {
                    Text("Location Available: ${state.location}")
                    Button(onClick = { /* Handle button click */ }) {
                        Text("Do Something")
                    }
                }
            }
        }
    }

    private fun locationEnabled(): Boolean {
        val locationManager =
            requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return LocationManagerCompat.isLocationEnabled(locationManager)
    }

    private fun requestLocationEnable() {
        activity?.let {
            val locationRequest = LocationRequest.create()
            val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
            LocationServices.getSettingsClient(it).checkLocationSettings(builder.build())
                .addOnSuccessListener {
                    if (it.locationSettingsStates?.isLocationPresent == true) {
                        viewModel.getCurrentLocation()
                    }
                }.addOnFailureListener {
                    if (it is ResolvableApiException) {
                        try {
                            it.startResolutionForResult(requireActivity(), 999)
                        } catch (e: IntentSender.SendIntentException) {
                            e.printStackTrace()
                        }
                    }
                }

        }
    }
}
*/