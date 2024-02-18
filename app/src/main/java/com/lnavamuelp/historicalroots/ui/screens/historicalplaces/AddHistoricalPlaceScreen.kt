package com.lnavamuelp.historicalroots.ui.screens.historicalplaces

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.lnavamuelp.historicalroots.R
import com.lnavamuelp.historicalroots.database.HistoricPlace
import com.lnavamuelp.historicalroots.ui.common.customComposableViews.CustomToolbarWithBackArrow
import com.lnavamuelp.historicalroots.ui.theme.CustomTextField
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


var placeId: Long = 0
var placeName: String =""
var short_description: String=""
var long_description: String =""
var imageUrl: String =""
var location: String=""
var category: String=""
var latitude: Double = 0.0
var longitude: Double= 0.0

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditHistoricalPlaceScreen(
    navController: NavController,
    placeId: String,
    isEdit: Boolean
) {



    var isNameValid by remember { mutableStateOf(true) }
    var isShortDescriptionValid by remember { mutableStateOf(true) }
    var isLongDescriptionValid by remember { mutableStateOf(true) }
    var isLocationValid by remember { mutableStateOf(true) }
    var isCategoryValid by remember { mutableStateOf(true) }
    var isLatitudeValid by remember { mutableStateOf(true) }
    var isLongitudeValid by remember { mutableStateOf(true) }


    fun validateName(name: String): Boolean {
        // Example validation: Name should not be empty
        return name.isNotBlank()
    }

    fun validateShortDescription(shortDescription: String): Boolean {
        // Example validation: Short description should have at least 10 characters
        return shortDescription.length >= 10
    }

    fun validateLongDescription(longDescription: String): Boolean {
        // Example validation: Long description should have at least 20 characters
        return longDescription.length >= 20
    }

    fun validateLocation(location: String): Boolean {
        // Example validation: Location should not be empty
        return location.isNotBlank()
    }

    fun validateCategory(category: String): Boolean {
        // Example validation: Category should not be empty
        return category.isNotBlank()
    }

    fun validateLatitude(latitude: Double): Boolean {
        // Example validation: Latitude should be between -90 and 90
        return latitude in -90.0..90.0
    }

    fun validateLongitude(longitude: Double): Boolean {
        // Example validation: Longitude should be between -180 and 180
        return longitude in -180.0..180.0
    }



    lateinit var selectedPlace: HistoricPlace
    val mContext = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var validationMessageShown by remember { mutableStateOf(false) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val bitmap = remember { mutableStateOf<Bitmap?>(null) }
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }

    clearAll()

    if (isEdit) {
        val historicalPlacesListViewModel: HistoricalPlacesListViewModel = hiltViewModel()
        historicalPlacesListViewModel.findPlaceById(placeId.toLong())
        selectedPlace = historicalPlacesListViewModel.foundPlace.observeAsState().value!!

        placeName = selectedPlace.name
        short_description = selectedPlace.short_description
        long_description = selectedPlace.long_description
        imageUrl = selectedPlace.imageUrl
        location = selectedPlace.location
        category = selectedPlace.category
        latitude = selectedPlace.latitude
        longitude = selectedPlace.longitude

    }

    // Shows the validation message.
    suspend fun showEditMessage() {
        if (!validationMessageShown) {
            validationMessageShown = true
            delay(3000L)
            validationMessageShown = false
        }
    }

    // Shows the validation message.
    suspend fun showValidationMessageForField(isValid: Boolean) {
        if (!isValid) {
            // Show validation message for a short duration
            // You can customize the duration and message as needed
            validationMessageShown = true
            delay(3000L)
            validationMessageShown = false
        }
    }

    val scrollState = rememberScrollState()


    var isEdited by remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            CustomToolbarWithBackArrow(
                title = if (isEdit) "Edit Historical Place" else "Add Historical Place",
                navController = navController
            )
        },
        content = {
            Surface(
                color = Color.White
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp)
                        .verticalScroll(state = scrollState),
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.jetpack_compose_logo),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        colorFilter = ColorFilter.tint(
                            colorResource(id = R.color.teal_200),
                        ),
                        modifier = Modifier
                            .clickable { galleryLauncher.launch("image/*") }
                            .fillMaxSize()
                            .clip(RoundedCornerShape(50)),
                    )
                    imageUri?.let {
                        if (Build.VERSION.SDK_INT < 28) {
                            bitmap.value = MediaStore.Images
                                .Media.getBitmap(mContext.contentResolver, it)

                        } else {
                            val source = ImageDecoder
                                .createSource(mContext.contentResolver, it)
                            bitmap.value = ImageDecoder.decodeBitmap(source)
                        }

                        bitmap.value?.let { btm ->
                            Image(
                                bitmap = btm.asImageBitmap(),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(120.dp)
                                    .clip(RoundedCornerShape(50)),
                            )
                        }
                    }
                    ValidationMessage(shown = validationMessageShown)

                    //Historical Place Name
                    CustomTextField(
                        modifier = Modifier
                            .padding(all = 10.dp)
                            .fillMaxWidth(),
                        labelResId = R.string.place_name,
                        inputWrapper = placeName,
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.None,
                            autoCorrect = false,
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        ),
                        maxLength = 50,
                        maxLines = 1
                    ) {
                        isEdited = true
                        placeName = it
                        isNameValid = validateName(it)

                    }

                    //Historical Place Short Description
                    CustomTextField(
                        modifier = Modifier
                            .padding(all = 10.dp)
                            .fillMaxWidth(),
                        labelResId = R.string.short_description,
                        inputWrapper = short_description,
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.None,
                            autoCorrect = false,
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        ),
                        maxLength = 100,
                        maxLines = 5
                    ) {
                        isEdited = true
                        short_description = it
                        isShortDescriptionValid = validateShortDescription(it)
                    }

                    //Historical Place Long Description
                    CustomTextField(
                        modifier = Modifier
                            .padding(all = 10.dp)
                            .fillMaxWidth(),
                        labelResId = R.string.long_description,
                        inputWrapper = long_description,
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.None,
                            autoCorrect = false,
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        ),
                        maxLength = 300,
                        maxLines = 10
                    ) {
                        isEdited = true
                        long_description = it
                        isLongDescriptionValid = validateLongDescription(it)
                    }

                    //Historical Place Location
                    CustomTextField(
                        modifier = Modifier
                            .padding(all = 10.dp)
                            .fillMaxWidth(),
                        labelResId = R.string.location,
                        inputWrapper = location,
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.None,
                            autoCorrect = false,
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        ),
                        maxLength = 50,
                        maxLines = 1
                    ) {
                        isEdited = true
                        location = it
                        isLocationValid = validateLocation(it)
                    }

                    //Historical Place Category
                    CustomTextField(
                        modifier = Modifier
                            .padding(all = 10.dp)
                            .fillMaxWidth(),
                        labelResId = R.string.category,
                        inputWrapper = category,
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.None,
                            autoCorrect = false,
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        ),
                        maxLength = 50,
                        maxLines = 1
                    ) {
                        isEdited = true
                        category = it
                        isCategoryValid = validateCategory(it)
                    }

                    //Historical Place Latitude
                    CustomTextField(
                        modifier = Modifier
                            .padding(all = 10.dp)
                            .fillMaxWidth(),
                        labelResId = R.string.latitude,
                        inputWrapper = latitude.toString(),
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.None,
                            autoCorrect = false,
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next
                        ),
                        maxLength = 50,
                        maxLines = 1
                    ) {
                        isEdited = true
                        latitude = it.toDouble()
                        isLatitudeValid = validateLatitude(it.toDouble())
                    }

                    //Historical Place Longitude
                    CustomTextField(
                        modifier = Modifier
                            .padding(all = 10.dp)
                            .fillMaxWidth(),
                        labelResId = R.string.longitude,
                        inputWrapper = longitude.toString(),
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.None,
                            autoCorrect = false,
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next
                        ),
                        maxLength = 50,
                        maxLines = 1
                    ) {
                        isEdited = true
                        longitude = it.toDouble()
                        isLongitudeValid = validateLongitude(it.toDouble())
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                    val historicalPlacesListViewModel: HistoricalPlacesListViewModel = hiltViewModel()
                    Button(onClick = {
                        //Log.d("AddEditHistoricalPlace", "Button clicked")
                        if (isEdited) {
                            if (isNameValid && isShortDescriptionValid && isLongDescriptionValid) {
                                val historicPlace = HistoricPlace(
                                placeId = if (isEdit) selectedPlace.placeId else 0,
                                name = placeName,
                                short_description = short_description,
                                long_description = long_description,
                                imageUrl = imageUrl,
                                location = location,
                                category = category,
                                latitude = latitude,
                                longitude = longitude
                            )
                            if (isEdit) {
                                updatePlaceInDB(
                                    mContext,
                                    navController as NavHostController,
                                    historicPlace,
                                    historicalPlacesListViewModel
                                )
                            } else {
                                addPlaceInDB(
                                    mContext,
                                    navController as NavHostController,
                                    historicPlace,
                                    historicalPlacesListViewModel
                                )
                            }
                            clearAll()
                            } else {
                                coroutineScope.launch {
                                    // Show validation message for the field that failed validation
                                    if (!isNameValid) {
                                        showValidationMessageForField(isNameValid)
                                    } else if (!isShortDescriptionValid) {
                                        showValidationMessageForField(isShortDescriptionValid)
                                    } else if (!isLongDescriptionValid) {
                                        showValidationMessageForField(isLongDescriptionValid)
                                    } else if (!isLocationValid) {
                                        showValidationMessageForField(isLocationValid)
                                    } else if (!isCategoryValid) {
                                        showValidationMessageForField(isCategoryValid)
                                    } else if (!isLatitudeValid) {
                                        showValidationMessageForField(isLatitudeValid)
                                    } else if (!isLongitudeValid) {
                                        showValidationMessageForField(isLongitudeValid)
                                    }
                                }
                            }
                        } else {
                            coroutineScope.launch {
                                showEditMessage()
                            }
                        }
                    }) {
                        Text(
                            text = if (isEdit) "Update Details" else "Add",
                            fontSize = 18.sp,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                        )
                    }
                }
            }
        }
    )
}

/**
 * Shows a validation message.
 */

@Composable
private fun ValidationMessage(shown: Boolean) {
    AnimatedVisibility(
        visible = shown,
        enter = slideInVertically(
            // Enters by sliding in from offset -fullHeight to 0.
            initialOffsetY = { fullHeight -> -fullHeight },
            animationSpec = tween(durationMillis = 150, easing = LinearOutSlowInEasing)
        ),
        exit = slideOutVertically(
            // Exits by sliding out from offset 0 to -fullHeight.
            targetOffsetY = { fullHeight -> -fullHeight },
            animationSpec = tween(durationMillis = 250, easing = FastOutLinearInEasing)
        )
    ) {
        Surface(
            modifier = androidx.compose.ui.Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.secondary
        ) {
            Text(
                text = "Please add or update detail",
                modifier = androidx.compose.ui.Modifier.padding(16.dp)
            )
        }
    }
}


fun clearAll() {
    placeId = 0
    placeName =""
    short_description=""
    long_description =""
    imageUrl =""
    location=""
    category=""
    latitude = 0.0
    longitude= 0.0
}


fun addPlaceInDB(
    context: Context,
    navController: NavHostController,
    historicPlace: HistoricPlace,
    historicalPlacesListViewModel: HistoricalPlacesListViewModel
) {
    historicalPlacesListViewModel.addHistoricPlace(historicPlace)
    navController.popBackStack()
}

fun updatePlaceInDB(
    context: Context,
    navController: NavHostController,
    historicPlace: HistoricPlace,
    historicalPlacesListViewModel: HistoricalPlacesListViewModel
) {
    historicalPlacesListViewModel.updateHistoricPlace(historicPlace)
    navController.popBackStack()
}