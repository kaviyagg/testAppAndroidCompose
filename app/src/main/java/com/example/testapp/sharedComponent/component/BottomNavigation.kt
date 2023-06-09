package com.example.testapp.sharedComponent.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.testapp.CustomBottomNavigationItem

@Composable
fun CustomBottomNavigation(
    items: List<CustomBottomNavigationItem>,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.White
) {
    Surface(
        modifier = modifier,
        color = backgroundColor
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            items.forEach { item ->
                CustomBottomNavigationItem(
                    item = item,
                    selected = item.selected,
                    onClick = item.onClick
                )
            }
        }
    }
}

@Composable
fun CustomBottomNavigationItem(
    item: CustomBottomNavigationItem,
    selected: Boolean,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(8.dp)
    ) {
        Icon(
            imageVector = item.icon,
            contentDescription = item.text,
            tint = if (selected)  Color.Black else Color.White
        )
        Text(
            text = item.text,
            color = if (selected)  Color.Black else Color.White
        )
    }
}
