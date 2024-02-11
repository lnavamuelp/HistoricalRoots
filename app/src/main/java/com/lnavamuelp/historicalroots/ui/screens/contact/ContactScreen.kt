package com.lnavamuelp.historicalroots.ui.screens.contact

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.lnavamuelp.historicalroots.ui.common.customComposableViews.CustomToolbar
import com.lnavamuelp.historicalroots.ui.theme.CustomTextField


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ContactUsScreen(
    navController: NavController,
    openDrawer: () -> Unit
) {
    val context = LocalContext.current
    var userMessage: String = ""
    var emailSubject = stringResource(id = com.lnavamuelp.historicalroots.R.string.contact_subject)

    Scaffold(
        topBar = {
            CustomToolbar(title = "Raise a Concern", openDrawer)
        },
        content = {
            Surface(
                color = Color.White,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp),
                ) {
                    Text(
                        "Have any concern or feedback? Share with us.",
                        fontSize = 16.sp,
                        modifier = Modifier
                            .wrapContentWidth()
                            .wrapContentHeight(),
                        textAlign = TextAlign.Center
                    )
                    CustomTextField(
                        modifier = Modifier
                            .padding(all = 10.dp)
                            .height(130.dp)
                            .fillMaxWidth(),
                        labelResId = com.lnavamuelp.historicalroots.R.string.contact_message,
                        inputWrapper = userMessage,
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.None,
                            autoCorrect = false,
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Done
                        ),
                        maxLength = 150,
                        maxLines = 5
                    ) {
                        userMessage = it
                    }
                    Button(
                        onClick = {
                            val addresses = arrayOf("abc@gmail.com")
                            composeEmail(context,addresses, emailSubject, userMessage)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = "Send",
                            fontSize = 16.sp,
                        )
                    }
                }
            }
        }
    )
}

fun composeEmail(context: Context, addresses: Array<String>, subject: String, message: String) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "message/rfc822"
        putExtra(Intent.EXTRA_EMAIL, addresses)
        putExtra(Intent.EXTRA_SUBJECT, subject)
        putExtra(Intent.EXTRA_TEXT, message)
    }
    ContextCompat.startActivity(context, Intent.createChooser(intent, "Send an email..."), null)
}
