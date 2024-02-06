package com.lnavamuelp.historicalroots.ui.screens.historicalplaces

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
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

data class FormField(
    val labelResId: Int,
    val inputWrapper: Any,
    val keyboardOptions: KeyboardOptions,
    val maxLength: Int,
    val maxLines: Int
)

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditHistoricalPlaceScreen(
    navController: NavController,
    placeToEdit: String?,
    isEdit: Boolean
) {
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
        historicalPlacesListViewModel.findPlaceById(placeToEdit!!)
        selectedPlace = historicalPlacesListViewModel.foundPlace.observeAsState().value!!
        placeId = selectedPlace.placeId
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

    val formFields = remember {
        listOf(
            FormField(
                R.string.place_name,
                placeName,
                KeyboardOptions(
                    capitalization = KeyboardCapitalization.None,
                    autoCorrect = false,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                50,
                1
            ),
            FormField(
                R.string.short_description,
                short_description,
                KeyboardOptions(
                    capitalization = KeyboardCapitalization.None,
                    autoCorrect = false,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                100,
                1
            ),
            FormField(
                R.string.long_description,
                long_description,
                KeyboardOptions(
                    capitalization = KeyboardCapitalization.None,
                    autoCorrect = false,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                100,
                1
            ),
            FormField(
                R.string.image_url,
                imageUrl,
                KeyboardOptions(
                    capitalization = KeyboardCapitalization.None,
                    autoCorrect = false,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                100,
                1
            ),
            FormField(
                R.string.location,
                location,
                KeyboardOptions(
                    capitalization = KeyboardCapitalization.None,
                    autoCorrect = false,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                100,
                1
            ),
            FormField(
                R.string.category,
                category,
                KeyboardOptions(
                    capitalization = KeyboardCapitalization.None,
                    autoCorrect = false,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                100,
                1
            ),
            FormField(
                R.string.latitude,
                latitude,
                KeyboardOptions(
                    capitalization = KeyboardCapitalization.None,
                    autoCorrect = false,
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                10,
                1
            ),
            FormField(
                R.string.longitude,
                longitude,
                KeyboardOptions(
                    capitalization = KeyboardCapitalization.None,
                    autoCorrect = false,
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                10,
                1
            )

        )
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
                        painter = painterResource(id = R.drawable.ic_launcher_background),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        colorFilter = ColorFilter.tint(
                            colorResource(id = R.color.purple_200),
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

                    formFields.forEach { field ->
                        CustomTextField(
                            modifier = Modifier
                                .padding(all = 10.dp)
                                .fillMaxWidth(),
                            labelResId = field.labelResId,
                            inputWrapper = field.inputWrapper.toString(),
                            keyboardOptions = field.keyboardOptions,
                            maxLength = field.maxLength,
                            maxLines = field.maxLines
                        ) {
                            isEdited = true
                            placeName = it
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                    val historicalPlacesListViewModel: HistoricalPlacesListViewModel = hiltViewModel()
                    Button(onClick = {
                        //Log.d("AddEditHistoricalPlace", "Button clicked")
                        if (isEdited) {
                            val historicPlace = createHistoricPlaceFromFormFields(formFields)
                            if (isEdit) {
                                historicalPlacesListViewModel.updateHistoricPlace(historicPlace)
                            } else {
                                historicalPlacesListViewModel.addHistoricPlace(historicPlace)
                            }
                            clearAll()
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

@OptIn(ExperimentalAnimationApi::class)
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

private fun createHistoricPlaceFromFormFields(formFields: List<FormField>): HistoricPlace {
    // Create HistoricPlace object from form field data
    return HistoricPlace(
        placeId = placeId,
        name = placeName,
        short_description = short_description,
        long_description = long_description,
        imageUrl = imageUrl,
        location = location,
        category = category,
        latitude = latitude,
        longitude = longitude
    )
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
