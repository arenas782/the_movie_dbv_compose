package com.example.themoviedb.ui.composables

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.themoviedb.R
import com.example.themoviedb.ui.tvShows.MainActivity
import com.example.themoviedb.utils.Constants
import com.example.themoviedb.utils.PreferencesManager

@Composable
fun LoginScreen() {
    val mContext = LocalContext.current
    val activity = (LocalContext.current as? Activity)

    Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {


        Image(painterResource(R.drawable.icon_logo),"content description")
        Text(
            text ="Mubi",
            style = TextStyle( color = MaterialTheme.colors.primary, fontSize = 36.sp),
            fontWeight = FontWeight.Bold
        )
        Text("Sign in to your account",
            modifier = Modifier.padding(16.dp))
        Button(onClick = {
            PreferencesManager.getInstance().putBoolean(Constants.LOGGED_IN,true)
            mContext.startActivity(Intent(mContext, MainActivity::class.java))
            activity?.finish()
        },
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text("LOG IN", modifier = Modifier.padding(8.dp),style = TextStyle(color = Color.White))
        }
    }
}
