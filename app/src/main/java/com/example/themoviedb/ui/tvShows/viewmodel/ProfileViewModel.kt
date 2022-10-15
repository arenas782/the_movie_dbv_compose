package com.example.themoviedb.ui.tvShows.viewmodel

import androidx.lifecycle.ViewModel
import com.example.themoviedb.data.api.TVService
import com.example.themoviedb.data.repository.TVShowsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(private val repository : TVShowsRepository) : ViewModel() {



}