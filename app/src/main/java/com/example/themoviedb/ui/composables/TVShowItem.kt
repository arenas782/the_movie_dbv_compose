package com.example.themoviedb.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.themoviedb.data.model.response.tvshows.TVShow
import com.example.themoviedb.utils.Commons
import com.example.themoviedb.utils.Constants

@Composable
fun TVShowItem(tvShowData: TVShow, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(
                bottom = 5.dp, top = 5.dp,
                start = 5.dp, end = 5.dp
            )
            .clickable(onClick = onClick)
            .height(240.dp),
        shape = RoundedCornerShape(15.dp),
        elevation = 12.dp,
    ) {
        Column(
            modifier = Modifier.fillMaxHeight()
        ) {
            Row(
                modifier = Modifier.height(160.dp)
            ){
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current).
                    data( if (tvShowData.poster_path == null) Constants.BASE_URL_POSTS.plus(tvShowData.backdrop_path) else Constants.BASE_URL_POSTS.plus(tvShowData.poster_path)).
                    crossfade(true).
                    build(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            }
            Row(
                modifier = Modifier.padding(top = 10.dp, start = 16.dp,end = 16.dp)
            ){
                Text(text =  tvShowData.name.toString(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis)
            }
            Row (
                modifier =  Modifier.padding(start = 16.dp, top = 8.dp, end = 8.dp)

            ){
                RatingBar( rating = if (tvShowData.vote_average!! > 0f) tvShowData.vote_average *0.5f else 0f)
                Text( text =  String.format("%.2f", tvShowData.vote_average *0.5f), modifier = Modifier.padding(start = 4.dp,top=2.dp))
            }
        }
    }
}