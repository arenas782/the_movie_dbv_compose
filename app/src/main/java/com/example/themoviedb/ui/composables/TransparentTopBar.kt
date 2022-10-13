package com.example.themoviedb.ui.composables

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun TransparentTopBar(    onClick : () -> Unit){
    TopAppBar(
        title = { Text("App") },
        backgroundColor = Color.Transparent.copy(alpha = 0f),
        navigationIcon = {
            IconButton(onClick = {
                onClick
            }) {
                Icon(Icons.Default.Menu, "Open/Close menu")
            }
        }
    )
}