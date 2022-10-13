package com.example.themoviedb.ui.composables

import android.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun LoadingIndicator() {
    Box(
        modifier = Modifier.fillMaxSize().background(color = LightGray),
        contentAlignment = Alignment.Center,

    ) {
        CircularProgressIndicator()
    }
}
