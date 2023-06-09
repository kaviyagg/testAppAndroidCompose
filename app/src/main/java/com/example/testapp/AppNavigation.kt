package com.example.testapp

import androidx.compose.ui.graphics.vector.ImageVector
object Routes {
    const val LOGIN = "login"
    const val PAIREDDEVICE = "paireddevice"
    const val SCANDEVICE = " scandevice"
    const val PROFILE = "profile"
    const val HISTORY = "history"
}

data class CustomBottomNavigationItem(
    val icon: ImageVector,
    val text: String,
    val selected: Boolean,
    val onClick: () -> Unit
)