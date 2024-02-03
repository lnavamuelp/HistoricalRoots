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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.lnavamuelp.historicalroots.R
import com.lnavamuelp.historicalroots.database.HistoricPlace


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoricalPlacesList(navController: NavController) {
    val viewModel: HistoricalPlacesListViewModel = hiltViewModel()
    val historicPlacesList by viewModel.historicPlacesList.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Historical Places") },
                actions = {
                    IconButton(onClick = {
                        // Handle actions if needed
                    }) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
                    }
                }
            )
        },
        content = {
            LazyColumn {
                if (historicPlacesList.isNotEmpty()) {
                    items(historicPlacesList) { historicPlace ->
                        PlaceCard(historicPlace = historicPlace, navController = navController)
                    }
                } else {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(20.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Text(
                                "Todavia no hay sitios historicos guardados.",
                                fontSize = 20.sp,
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .wrapContentHeight(),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    )
}

/**
 * Returns whether the lazy list is currently scrolling up.
 */
@Composable
private fun LazyListState.isScrollingUp(): Boolean {
    var previousIndex by remember(this) { mutableStateOf(firstVisibleItemIndex) }
    var previousScrollOffset by remember(this) { mutableStateOf(firstVisibleItemScrollOffset) }
    return remember(this) {
        derivedStateOf {
            if (previousIndex != firstVisibleItemIndex) {
                previousIndex > firstVisibleItemIndex
            } else {
                previousScrollOffset >= firstVisibleItemScrollOffset
            }.also {
                previousIndex = firstVisibleItemIndex
                previousScrollOffset = firstVisibleItemScrollOffset
            }
        }
    }.value
}

@Composable
fun PlaceCard(historicPlace: HistoricPlace, navController: NavController) {
    val expanded by remember { mutableStateOf(true) }
    Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(
            modifier = Modifier
                )
         { // Fix: Remove extra parenthesis
            Row {
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_background),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    colorFilter = ColorFilter.tint(
                        colorResource(id = R.color.purple_200),
                    ),
                    modifier = Modifier
                        .size(50.dp)
                        .clip(RoundedCornerShape(50)),
                )
                Spacer(modifier = Modifier.width(20.dp))
                Column {
                    Text(
                        text = historicPlace.name,
                        fontSize = 18.sp,
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    if (expanded) {
                        Text(
                            text = historicPlace.category,
                            fontSize = 14.sp,
                        )
                    }
                }
            }
        }
    }
}
