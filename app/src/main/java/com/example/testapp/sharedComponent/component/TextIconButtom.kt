package com.example.testapp.sharedComponent.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp



@Composable
fun TextIconButton(
    label: String,
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    enabled: Boolean = true,
    onClick: () -> Unit = {},
) {
    var modifier = Modifier.padding(0.dp)
    if(trailingIcon != null) {
        modifier = modifier.padding(end = 8.dp)
    }
    TextButton(
        onClick = onClick, enabled = enabled
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (leadingIcon != null) {
                modifier = modifier.padding(start = 8.dp)
                Icon(leadingIcon, contentDescription = label)
            }
            Text(
                label.uppercase(),
                modifier,
                style = TextStyle.Default
            )
            if (trailingIcon != null) {
                Icon(trailingIcon, contentDescription = label)
            }
        }
    }
}

@Preview
@Composable
fun TextIconButtonPreview() {
    TextIconButton("Label", Icons.Filled.Add)
}