package com.example.testapp.sharedComponent.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue


@Composable
fun ConfirmDialog(
    showState: Boolean,
    title: String? = null,
    message: String? = null,
    onConfirm: (() -> Unit)? = null,
    onCancel: (() -> Unit)? = null,
) {
    var openDialog by remember { mutableStateOf(showState) }
    if (openDialog) {
        AlertDialog(
            onDismissRequest = {
                !showState
            },
            title = { if (title != null) Text(text = title) },
            text = { if (message != null) Text(text = message) },
            confirmButton = {
                TextButton(
                    onClick = {
                        openDialog = false
                        if (onConfirm != null) {
                            onConfirm()
                        }
                    }
                ) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        openDialog = false
                        if (onCancel != null) {
                            onCancel()
                        }
                    }
                ) {
                    Text("Dismiss")
                }
            }
        )
    }
}
