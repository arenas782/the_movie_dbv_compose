package com.example.themoviedb.ui.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.themoviedb.R
import com.example.themoviedb.data.model.response.tvshows.TVShowSeason
import com.example.themoviedb.utils.Constants

@Composable
fun TVShowSeason(season : TVShowSeason){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .height(160.dp)
    ) {
        Row(){
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current).
                data( Constants.BASE_URL_POSTS.plus(season.poster_path)).
                crossfade(true).
                placeholder(R.drawable.placeholder).
                build(),

                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.width(120.dp)
            )
            Column() {
                Text(
                    text =season.name.toString(), modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    style = TextStyle(fontSize = 18.sp)
                )

                Text(
                    text ="${season.episode_count} episodes", modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    style = TextStyle(color = MaterialTheme.colors.primary)
                )
                Text(
                    text =season.overview.toString(), modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp, horizontal = 16.dp),
                    style = TextStyle(fontSize = 12.sp),
                    maxLines = 4,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}