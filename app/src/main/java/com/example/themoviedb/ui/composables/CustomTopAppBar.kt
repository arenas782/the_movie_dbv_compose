package com.example.themoviedb.ui.composables

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.themoviedb.ui.theme.Purple500
import com.example.themoviedb.ui.tvShows.enums.SearchWidgetState
import com.example.themoviedb.ui.tvShows.viewmodel.TVShowsViewModel


@Composable
fun CustomTopAppBar(viewModel : TVShowsViewModel,onProfileButtonClicked :() ->Unit) {
    androidx.compose.material.TopAppBar(

        title = { Text(text = "TV Shows") },
        actions = {
            IconButton(onClick = {
                viewModel.updateSearchWidgetState(SearchWidgetState.OPENED)
            }) {
                Icon(imageVector = Icons.Filled.Search, contentDescription = "Buscar")
            }
            IconButton(onClick = onProfileButtonClicked) {
                Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = "Buscar")
            }
        },
        backgroundColor = Purple500,
        contentColor = Color.White
    )
}