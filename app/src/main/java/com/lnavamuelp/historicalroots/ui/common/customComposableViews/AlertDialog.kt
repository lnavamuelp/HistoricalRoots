package com.lnavamuelp.historicalroots.ui.common.customComposableViews

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun CreateAlertDialog(
    titleText: String? = null,
    bodyText: String,
    confirmButtonText: String,
    onConfirm: () -> Unit,
    cancelButtonText: String,
    onCancel: () -> Unit,
    onDismiss: () -> Unit
) {

    val title: @Composable (() -> Unit)? = if (titleText.isNullOrBlank())
        null
    else {
        { Text(text = titleText) }
    }
    AlertDialog(
        title = title,
        text = {
            Text(
                text = bodyText
            )
        },
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onConfirm()
                onDismiss()
            }) {
                Text(text = confirmButtonText)
            }
        },
        dismissButton = {
            TextButton(onClick = {
                onCancel()
                onDismiss()
            }) {
                Text(text = cancelButtonText)
            }
        }
    )
}
