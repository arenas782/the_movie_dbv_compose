package com.example.themoviedb.ui.tvShows.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.themoviedb.data.api.TVService
import com.example.themoviedb.data.model.TVShow
import com.example.themoviedb.data.model.response.MovieDetailsResponse
import com.example.themoviedb.data.repository.TVShowsRepository
import com.example.themoviedb.ui.tvShows.Filters
import com.example.themoviedb.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TVShowsViewModel @Inject constructor(private val repository : TVShowsRepository,private val service : TVService) : ViewModel() {


    val selectedFilter : MutableState<String> = mutableStateOf(Filters.TOP_RATED.value)


    private var _movieDetails = MutableLiveData<Resource<MovieDetailsResponse>>()
    var movieDetails : LiveData<Resource<MovieDetailsResponse>> = _movieDetails

    private val _tvShows = MutableStateFlow(emptyFlow<PagingData<TVShow>>())
    val tvshows: StateFlow<Flow<PagingData<TVShow>>> = _tvShows

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun updateFilter(filter : String) = effect {

            selectedFilter.value = filter
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
        _tvShows.value =  repository.getTVShowsPerFilter(Filters.TOP_RATED.value).cachedIn(viewModelScope)        // 3
        _isLoading.value = false
    }






//
//    fun getTopRatedTVShows(): Flow<PagingData<TVShow>> {
//        val newResult: Flow<PagingData<TVShow>> =
//            repository.getMovies().cachedIn(viewModelScope)
//        currentResult = newResult
//
//        return newResult
//    }

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

//     fun deleteMovie(id : Int){
//         CoroutineScope(Dispatchers.IO).launch {
//             Log.e("TAG","deleting post")
//             repository.deleteMovieById(id)
//         }
//    }

}