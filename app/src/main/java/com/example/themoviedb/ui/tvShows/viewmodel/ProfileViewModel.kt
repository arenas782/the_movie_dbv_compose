package com.example.themoviedb.ui.tvShows.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.themoviedb.data.model.response.tvshows.TVShow
import com.example.themoviedb.data.repository.TVShowsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(private val repository : TVShowsRepository) : ViewModel() {


    private val _favoriteTVShows = MutableStateFlow(emptyList<TVShow>())
    val favoriteTVShows: StateFlow<List<TVShow>> = _favoriteTVShows

    private fun effect(block: suspend () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) { block() }    // 4
    }

    init {
       getFavorites()
    }

    private fun getFavorites()=  effect {

        repository.getFavoriteTVShows().collectLatest{
            _favoriteTVShows.value = it
        }
    }

}