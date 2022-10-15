package com.example.themoviedb.ui.composables

import androidx.compose.runtime.Composable
import com.example.themoviedb.ui.tvShows.enums.SearchWidgetState
import com.example.themoviedb.ui.tvShows.viewmodel.TVShowsViewModel

@Composable
fun TVShowsAppBar(
    searchWidgetState: SearchWidgetState,
    searchTextState : String,
    onTextChanged : (String) -> Unit,
    onCloseClicked : () -> Unit,
    viewModel : TVShowsViewModel
){
    when (searchWidgetState){
        SearchWidgetState.CLOSED -> {
            CustomTopAppBar(viewModel)
        }
        SearchWidgetState.OPENED -> {
            SearchAppBar(
                text = searchTextState,
                onTextChanged = onTextChanged,
                onCloseClicked = onCloseClicked,
            )
        }
    }
}