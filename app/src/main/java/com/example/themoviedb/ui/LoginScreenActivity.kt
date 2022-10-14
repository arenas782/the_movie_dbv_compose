package com.example.themoviedb.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.themoviedb.ui.composables.LoginScreen
import com.example.themoviedb.ui.composables.SplashScreen

class LoginScreenActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Calling the composable function
            // to display element and its contents
            val navController = rememberNavController()
            SetupNavGraph(navController = navController)
        }
    }


    // the Android Studio IDE emulator
    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        LoginScreen()
    }

    @Composable
    fun SetupNavGraph(navController: NavHostController) {
        NavHost(
            navController = navController,
            startDestination = DestinationScreen.SplashScreenDest.route
        ) {
            composable(route = DestinationScreen.SplashScreenDest.route) {
                SplashScreen(navController = navController)
            }
            composable(route = DestinationScreen.LoginScreenDest.route) {
                LoginScreen()
            }
        }
    }



    sealed class DestinationScreen(val route: String) {
        object SplashScreenDest : DestinationScreen(route = "splash_screen")
        object LoginScreenDest : DestinationScreen(route = "login_screen")
    }
}