package com.lnavamuelp.historicalroots.ui.theme

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(
    modifier: Modifier,
    keyboardOptions: KeyboardOptions = remember { KeyboardOptions.Default },
    inputWrapper: String,
    @StringRes labelResId: Int,
    maxLength: Int,
    maxLines: Int,
    onTextChanged: (String) -> Unit
) {
    // State to hold the value of the text field
    var fieldValue by remember { mutableStateOf(inputWrapper) }

    // Access the LocalFocusManager
    val focusManager = LocalFocusManager.current
    // Compose Column to organize components vertically
    Column {
            OutlinedTextField(
            value = fieldValue,
            label = { Text(stringResource(labelResId), style = MaterialTheme.typography.labelLarge) },
            maxLines = maxLines,
            keyboardOptions = keyboardOptions,
            modifier = modifier,
            onValueChange = {
                // Update the field value when the text changes
                if (it.length <= maxLength) {
                    fieldValue = it
                    onTextChanged(it)
                }
            },
            keyboardActions = KeyboardActions(
                onNext = {
                    // Move focus to the next focusable element when "Next" key is pressed
                    focusManager.moveFocus(FocusDirection.Down)
                },
                onDone = {
                    // Clear focus when "Done" key is pressed
                    focusManager.clearFocus()
                }
            ),
        )
    }
}
