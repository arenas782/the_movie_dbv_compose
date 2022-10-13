package com.example.themoviedb.ui.tvShows.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import androidx.paging.*
import com.example.themoviedb.data.api.TVService
import com.example.themoviedb.data.model.response.tvshows.TVShow
import com.example.themoviedb.data.model.response.tvshows.TVShowDetailsResponse
import com.example.themoviedb.data.repository.TVShowsRepository
import com.example.themoviedb.ui.tvShows.Filters
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TVShowsViewModel @Inject constructor(private val repository : TVShowsRepository,private val service : TVService) : ViewModel() {


    val selectedFilter : MutableState<String> = mutableStateOf(Filters.TOP_RATED.value)

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()



    private val _tvShows = MutableStateFlow(emptyFlow<PagingData<TVShow>>())
    val tvshows: StateFlow<Flow<PagingData<TVShow>>> = _tvShows



    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    val selectedTVShowId : MutableState<Int?> = mutableStateOf(null)

    val tvShowDetails: MutableLiveData<TVShowDetailsResponse?> by lazy {
        MutableLiveData<TVShowDetailsResponse?>()
    }
    val users:TVShowDetailsResponse? = null



    fun updateFilter(filter : String) = effect {
            selectedFilter.value = filter
    }



    fun getTVShowDetails(tvShowId : String) = effect{
        repository.getTVShowDetails(tvShowId.toInt()).collect{
            response -> tvShowDetails.postValue(response)
        }
    }


    fun newSearch()= effect {
            _isLoading.value = true
            _tvShows.value = repository.getTVShowsPerFilter(selectedFilter.value).cachedIn(viewModelScope)
            _isLoading.value = false
        }

    private val _search = MutableStateFlow(null as String?)
    val search: StateFlow<String?> = _search

    fun search(term: String?) {
        _search.value = term
    }





    private fun effect(block: suspend () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) { block() }    // 4
    }
    fun load() = effect {
        _isLoading.value = true
        _tvShows.value =  repository.getTVShowsPerFilter(selectedFilter.value).cachedIn(viewModelScope)        // 3
        _isLoading.value = false
    }




//    fun getMovieDetails(movieId : Int) = liveData(Dispatchers.Main) {
//            emit(Resource.loading(data = null))
//            try {
//                val movieDetails = repository.getMovieDetails(tvShowId = movieId)
//                Log.e("movie",movieDetails.toString())
//                emit(Resource.success(data = movieDetails))
//            } catch (exception: Exception) {
//                emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
//            }
//    }


    sealed class TVShowDetailState {
        object Empty : TVShowDetailState()
        object Loading : TVShowDetailState()
        class Loaded(val data: TVShowDetailsResponse) : TVShowDetailState()
        class Error(val message: String) : TVShowDetailState()
    }
}