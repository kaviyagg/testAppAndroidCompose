package com.example.testapp.sharedComponent.component

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CenteredTitleLayout(
    title: String,
    onBack: () -> Unit = {},
    onTitleClick: (() -> Unit)? = null,
    content: @Composable () -> Unit = {},
    bottomNavigation: @Composable () -> Unit = {},
) {
    val interactionSource = remember { MutableInteractionSource() }
    val modifier: Modifier = Modifier
    val textModifier = if (onTitleClick != null) {
        modifier.clickable(interactionSource = interactionSource, indication = null) {
            onTitleClick()
        }
    } else {
        modifier
    }
    val context: Context = LocalContext.current

    Scaffold(topBar = {
        Column {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.Blue),
                title = {
                    Text(
                        text = title.uppercase(),
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        modifier = textModifier
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.Filled.ArrowBack, contentDescription = "Back", tint = Color.White
                        )
                    }
                },
            )
        }
    }, bottomBar = {
        bottomNavigation()
    }, content = {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .background(MaterialTheme.colorScheme.surface)
        ) {
            content()
        }
    })
}
@Preview
@Composable
fun CenteredTitleLayoutPreview() {
    CenteredTitleLayout(title = "Title")
}
