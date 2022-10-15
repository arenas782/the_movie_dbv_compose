package com.example.themoviedb.ui.composables


import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.example.themoviedb.R
import com.example.themoviedb.data.model.response.tvshows.TVShow
import com.example.themoviedb.utils.Constants


@Composable
fun FavoriteTVShowItem(tvShowData: TVShow, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(
                5.dp
            )
            .clickable(onClick = onClick)
            .width(200.dp)
            .fillMaxHeight(),
        shape = RoundedCornerShape(15.dp),
        elevation = 12.dp,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Row(
                modifier = Modifier
                    .height(160.dp)
                    .fillMaxWidth()
                    .weight(1f),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically

            ){


                Box(contentAlignment = Alignment.Center) {
                    val imageUrl = if (tvShowData.poster_path == null) Constants.BASE_URL_POSTS.plus(tvShowData.backdrop_path) else Constants.BASE_URL_POSTS.plus(tvShowData.poster_path)

                    val painter = rememberAsyncImagePainter(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(imageUrl)
                            .size(Size.ORIGINAL) // Set the target size to load the image at.
                            .crossfade(true)

                            .build()
                    )

                    if (painter.state !is AsyncImagePainter.State.Success) {

                        Log.e("PAINTER STATE LOADING","${painter.state}")
                        Icon(
                            painter = painterResource(id = R.drawable.placeholder),
                            contentDescription = null,

                            )
                    }
                    Image(
                        painter = painter,
                        contentScale = ContentScale.FillWidth,
                        contentDescription = null,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
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
                RatingBar( modifier = Modifier.padding(bottom=12.dp) ,rating = if (tvShowData.vote_average!! > 0f) tvShowData.vote_average *0.5f else 0f)
                Text( text =  String.format("%.2f", tvShowData.vote_average *0.5f), modifier = Modifier.padding(start = 4.dp,top=2.dp))
            }
        }
    }
}