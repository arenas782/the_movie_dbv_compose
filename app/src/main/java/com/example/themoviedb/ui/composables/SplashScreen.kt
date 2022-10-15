package com.example.themoviedb.ui.composables

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.themoviedb.R
import com.example.themoviedb.ui.LoginScreenActivity
import com.example.themoviedb.ui.tvShows.MainActivity
import com.example.themoviedb.utils.Constants
import com.example.themoviedb.utils.PreferencesManager

@Composable
fun SplashScreen(navController: NavHostController) {
    val activity = (LocalContext.current as? Activity)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF31B5FE),
                        Color(0xFF6243FF),
                        Color(0xFF6243FF)
                    )
                )
            ), contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp).fillMaxWidth()){
            Box(modifier = Modifier.width(180.dp)){
                val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.play_button))
                val logoAnimationState =
                    animateLottieCompositionAsState(composition = composition)
                LottieAnimation(
                    composition = composition,
                    progress = { logoAnimationState.progress }
                )
                if (logoAnimationState.isAtEnd && logoAnimationState.isPlaying) {
                    if(PreferencesManager.getInstance().getBoolean(Constants.LOGGED_IN,false)){
                        LocalContext.current.startActivity(Intent( LocalContext.current, MainActivity::class.java))
                        activity?.finish()

                    }
                    else{
                        navController.navigate(LoginScreenActivity.DestinationScreen.LoginScreenDest.route) {
                            popUpTo(LoginScreenActivity.DestinationScreen.SplashScreenDest.route) {
                                inclusive = true
                            }
                        }
                    }
                }
            }

            Text("Mubi", style = TextStyle(fontWeight = FontWeight.Bold,
                fontSize = 24.sp, color = Color.White),
                modifier = Modifier.fillMaxWidth())
        }
    }
}