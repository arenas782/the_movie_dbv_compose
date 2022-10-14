package com.example.themoviedb.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightThemeColors = lightColors(

    primary = Purple500,
    primaryVariant = Purple700,
    onPrimary = Black2,
    secondary = Color.White,
    secondaryVariant = Teal300,
    onSecondary = Color.Black,
    error = RedErrorDark,
    onError = RedErrorLight,
    background = Grey1,
    onBackground = Color.Black,
    surface = Color.White,
    onSurface = Black2
)

private val DarkThemeColors = darkColors(
    primary = Purple500,
    primaryVariant = Color.White,
    onPrimary = Color.White,
    onSecondary = Black1,
    error = RedErrorLight,
    background = Color.Black,
    onBackground = Color.White,
    surface = Black1,
    onSurface = Color.White
)

@Composable
fun AppTheme(darkTheme : Boolean,
content : @Composable () -> Unit){
    MaterialTheme(colors = if(darkTheme) DarkThemeColors else LightThemeColors) {
        content()
    }
}